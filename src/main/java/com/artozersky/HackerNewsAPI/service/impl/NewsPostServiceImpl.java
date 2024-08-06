package com.artozersky.HackerNewsAPI.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsCreateDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsUpdateDTOImpl;
import com.artozersky.HackerNewsAPI.exception.CustomNotFoundException;
import com.artozersky.HackerNewsAPI.exception.CustomServiceException;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.NewsPostService;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import java.util.Comparator;  

import jakarta.validation.Valid;

@Service
public class NewsPostServiceImpl implements NewsPostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    // @Autowired
    private CacheEntityImpl cacheService;

    @Autowired
    public NewsPostServiceImpl(@Value("${cache.size:100}") int cacheSize) {
        this.cacheService = new CacheEntityImpl(cacheSize);
    }
    
    @Override
    public  NewsPostsResponseDTOImpl getPostById(Long postId) {
        try{
            // Step 1: Try to get the NewsPostModel from the cache
            NewsPostModelImpl cachedValue = cacheService.get(postId);
            if (cachedValue != null) {
                System.out.println("Cache hit: Retrieved from Cache: " + cachedValue);
                // Convert to PostResponseDTO before returning
                return convertToDTO(cachedValue);
            }

            // Step 2: Fetch the NewsPostModel from the database if not in cache
            NewsPostModelImpl postModel = postRepository.findById(postId)
                .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));

            // Step 3: Store the NewsPostModel in the cache
            cacheService.put(postId, postModel);
            System.out.println("Storing in Cache: key = " + postId + ", value = " + postModel);

            // Step 4: Convert to PostResponseDTO and return
            return convertToDTO(postModel);

        } catch (Exception e) {
            // Handle and log exceptions
            throw new CustomServiceException("An error occurred while retrieving the post with id: " + postId, e);
        }
    }

    @Override
    public List<NewsPostsResponseDTOImpl> getAllPosts() {

        // Step 1: Try to get the list of NewsPostModel from the cache
        List<NewsPostModelImpl> cachedPosts = cacheService.getAll();

        if (!cachedPosts.isEmpty() && (cachedPosts.size() >= postRepository.count())) {

            boolean isStale = cachedPosts.stream().anyMatch(post -> {
                int currentElapsedTime = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
                return Math.abs(post.getTimeElapsed() - currentElapsedTime) > 1;//more than one hou difference
            });
            if(!isStale) {
                System.out.println("Cache hit: Retrieved all posts from Cache");
                return cachedPosts.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            }
            else{
                cacheService.clear();
                System.out.println("Cache invalidated due to stale data");
            }
        }
        // Step 2: Fetch the list of NewsPostModel from the database if not in cache
        List<NewsPostModelImpl> allPosts = postRepository.findAll();

        // Step 3: Store the list in the cache
        cacheService.putAll(allPosts);
        System.out.println("Storing in Cache:  value = " + allPosts);

        // Step 4: Convert to PostResponseDTO and return
        return allPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsPostsResponseDTOImpl> getSortedPostsByScore() {
        // Step 1: Try to get the list of NewsPostModel from the cache
        List<NewsPostModelImpl> cachedPosts = cacheService.getAll();

        if (!cachedPosts.isEmpty() && cachedPosts.size() >= postRepository.count()) {
            // Check if the cached data is stale by comparing `timeElapsed`
            boolean isStale = cachedPosts.stream().anyMatch(post -> {
                int currentElapsedTime = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
                return Math.abs(post.getTimeElapsed() - currentElapsedTime) > 1;
            });

            if (!isStale) {
                System.out.println("Cache hit: Retrieved all posts from Cache");

                // Sort the cached posts by score and convert to PostResponseDTO
                return cachedPosts.stream()
                        .sorted(Comparator.comparingDouble(NewsPostModelImpl::getScore).reversed())
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                // Invalidate the cache if data is stale
                cacheService.clear();
                System.out.println("Cache invalidated due to stale data");
            }
        }

        // Step 2: Fetch the list of NewsPostModel from the database if not in cache or if cache is stale
        List<NewsPostModelImpl> allPosts = postRepository.findAllByOrderByScoreDesc();

        // Step 3: Store the list in the cache
        cacheService.putAll(allPosts);
        System.out.println("Storing in Cache: value = " + allPosts);

        // Step 4: Convert to PostResponseDTO and return
        return allPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    
    @Override
    public NewsPostsResponseDTOImpl savePost(@Valid NewsPostsCreateDTOImpl postCreateDTO) {
        
        NewsPostModelImpl post = modelMapper.map(postCreateDTO, NewsPostModelImpl.class);
        
        post.initialize();
        
        NewsPostModelImpl savedPost = postRepository.save(post);
        
        NewsPostsResponseDTOImpl responseDTO = modelMapper.map(savedPost, NewsPostsResponseDTOImpl.class);
        
        responseDTO.setMessage("Post created successfully");
        
        return responseDTO;
    }

    @Override
    public NewsPostsResponseDTOImpl updatePost(NewsPostsUpdateDTOImpl postUpdateDTO, Long postId) {
        
        NewsPostModelImpl post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
        
        boolean isRedundant = true;
        
        if (postUpdateDTO.getTitle() != null && !postUpdateDTO.getTitle().trim().isEmpty()) {
            if (!postUpdateDTO.getTitle().equals(post.getTitle())) {
                post.setTitle(postUpdateDTO.getTitle());
                isRedundant = false;
            }
        }
        
        if (postUpdateDTO.getUrl() != null && !postUpdateDTO.getUrl().trim().isEmpty()) {
            if (!postUpdateDTO.getUrl().equals(post.getUrl())) {
                post.setUrl(postUpdateDTO.getUrl());
                isRedundant = false;
            }
        }
        
        if(isRedundant) {
            NewsPostsResponseDTOImpl responseDTO = modelMapper.map(post, NewsPostsResponseDTOImpl.class);
            responseDTO.setMessage("No changes detected. Update skipped.");
            return responseDTO;
        }

        post.onPostUpdate();
        
        NewsPostModelImpl updatedPost = postRepository.save(post);
        
        //if in the cache update the cache
        if (cacheService.get(postId) != null) {
            cacheService.put(postId, updatedPost);
        }

        NewsPostsResponseDTOImpl responseDTO = modelMapper.map(updatedPost, NewsPostsResponseDTOImpl.class);
        responseDTO.setMessage("Post updated successfully");

        
        return responseDTO;
    }
    
    @Override
    public NewsPostsResponseDTOImpl upVote(Long id) {
        NewsPostModelImpl post = postRepository.findById(id)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.upVote();        
        NewsPostModelImpl updatedPost = postRepository.save(post);

        //if in the cache update the cache
        if (cacheService.get(id) != null) {
            cacheService.put(id, updatedPost);
        }

        NewsPostsResponseDTOImpl responseDTO = modelMapper.map(updatedPost, NewsPostsResponseDTOImpl.class);

        responseDTO.setMessage("Vote updated successfully");
        
        return responseDTO;
    }
    
    @Override
    public NewsPostsResponseDTOImpl downVote(Long id) {
        NewsPostModelImpl post = postRepository.findById(id)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.downVote();        
        NewsPostModelImpl updatedPost = postRepository.save(post);

        //if in the cache update the cache
        if (cacheService.get(id) != null) {
            cacheService.put(id, updatedPost);
        }

        NewsPostsResponseDTOImpl responseDTO = modelMapper.map(updatedPost, NewsPostsResponseDTOImpl.class);
        responseDTO.setMessage("Vote updated successfully");
        
        return responseDTO;
    }
    
    @Override
    public NewsPostsResponseDTOImpl deletePost(Long postId) {
        
        NewsPostModelImpl post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
        
        postRepository.delete(post);

        cacheService.evict(postId);//from cache
    
        NewsPostsResponseDTOImpl responseDTO = new NewsPostsResponseDTOImpl();
        responseDTO.setPostId(postId);
        responseDTO.setMessage("Post deleted successfully");
    
        return responseDTO;
    }

    private NewsPostsResponseDTOImpl convertToDTO(NewsPostModelImpl postModel) {
        NewsPostsResponseDTOImpl responseDTO = modelMapper.map(postModel, NewsPostsResponseDTOImpl.class);
        responseDTO.setMessage("Successfully fetched " + postModel.getPostId());
        return responseDTO;
    }
}
