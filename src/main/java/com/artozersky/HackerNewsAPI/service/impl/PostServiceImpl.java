package com.artozersky.HackerNewsAPI.service.impl;

import java.util.List;



// import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.artozersky.HackerNewsAPI.cache.CacheEntityManager;
import com.artozersky.HackerNewsAPI.cache.SimpleCache;
import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.exception.CustomNotFoundException;
import com.artozersky.HackerNewsAPI.exception.CustomServiceException;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.NewsPostService;
import java.util.stream.Collectors;

import java.util.Comparator;  

import jakarta.validation.Valid;

@Service
public class PostServiceImpl implements NewsPostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    // @Autowired
    private SimpleCache cacheService;

    @Autowired
    public PostServiceImpl() {
        this.cacheService = new SimpleCache(100);
    }
    
    @Override
    public  PostResponseDTO getPostById(Long postId) {
        try{
        // Step 1: Try to get the NewsPostModel from the cache
        NewsPostModel cachedValue = cacheService.get(postId);
        if (cachedValue != null) {
            System.out.println("Cache hit: Retrieved from Cache: " + cachedValue);
            // Convert to PostResponseDTO before returning
            return convertToDTO(cachedValue);
        }

        // Step 2: Fetch the NewsPostModel from the database if not in cache
        NewsPostModel postModel = postRepository.findById(postId)
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
    public List<PostResponseDTO> getAllPosts() {

        // Step 1: Try to get the list of NewsPostModel from the cache
        List<NewsPostModel> cachedPosts = cacheService.getAll();

        if (!(cachedPosts.isEmpty())) {
            System.out.println("Cache hit: Retrieved all posts from Cache");
            return cachedPosts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        // Step 2: Fetch the list of NewsPostModel from the database if not in cache
        List<NewsPostModel> allPosts = postRepository.findAll();

        // Step 3: Store the list in the cache
        cacheService.putAll(allPosts);
        System.out.println("Storing in Cache:  value = " + allPosts);

        // Step 4: Convert to PostResponseDTO and return
        return allPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDTO> getSortedPostsByScore() {

        // Step 1: Try to get the list of NewsPostModel from the cache
        List<NewsPostModel> cachedPosts = cacheService.getAll();

        if (!cachedPosts.isEmpty()) {
            System.out.println("Cache hit: Retrieved all posts from Cache");

            // Sort the cached posts by score and convert to PostResponseDTO
            return cachedPosts.stream()
                    .sorted(Comparator.comparingDouble(NewsPostModel::getScore).reversed())
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        // Step 2: Fetch the list of NewsPostModel from the database if not in cache
        List<NewsPostModel> allPosts = postRepository.findAllByOrderByScoreDesc();

        // Step 3: Store the list in the cache
        cacheService.putAll(allPosts);
        System.out.println("Storing in Cache: value = " + allPosts);

        // Step 4: Sort the posts by score, convert to PostResponseDTO, and return
        return allPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    
    @Override
    public PostResponseDTO savePost(@Valid PostCreateDTO postCreateDTO) {
        
        NewsPostModel post = modelMapper.map(postCreateDTO, NewsPostModel.class);
        
        post.initialize();
        
        NewsPostModel savedPost = postRepository.save(post);
        
        PostResponseDTO responseDTO = modelMapper.map(savedPost, PostResponseDTO.class);
        
        responseDTO.setMessage("Post created successfully");
        
        return responseDTO;
    }

    @Override
    public PostResponseDTO updatePost(PostUpdateDTO postUpdateDTO, Long postId) {
        
        NewsPostModel post = postRepository.findById(postId)
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
            PostResponseDTO responseDTO = modelMapper.map(post, PostResponseDTO.class);
            responseDTO.setMessage("No changes detected. Update skipped.");
            return responseDTO;
        }

        post.onPostUpdate();
        
        NewsPostModel updatedPost = postRepository.save(post);
        
        //if in the cache update the cache
        if (cacheService.get(postId) != null) {
            cacheService.put(postId, updatedPost);
        }

        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);
        responseDTO.setMessage("Post updated successfully");

        
        return responseDTO;
    }
    
    @Override
    public PostResponseDTO upVote(Long id) {
        NewsPostModel post = postRepository.findById(id)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.upVote();        
        NewsPostModel updatedPost = postRepository.save(post);

        //if in the cache update the cache
        if (cacheService.get(id) != null) {
            cacheService.put(id, updatedPost);
        }

        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);

        responseDTO.setMessage("Vote updated successfully");
        
        return responseDTO;
    }
    
    @Override
    public PostResponseDTO downVote(Long id) {
        NewsPostModel post = postRepository.findById(id)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.downVote();        
        NewsPostModel updatedPost = postRepository.save(post);

        //if in the cache update the cache
        if (cacheService.get(id) != null) {
            cacheService.put(id, updatedPost);
        }

        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);
        responseDTO.setMessage("Vote updated successfully");
        
        return responseDTO;
    }
    
    @Override
    public PostResponseDTO deletePost(Long postId) {
        
        NewsPostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
        
        postRepository.delete(post);

        cacheService.evict(postId);//from cache
    
        PostResponseDTO responseDTO = new PostResponseDTO();
        responseDTO.setPostId(postId);
        responseDTO.setMessage("Post deleted successfully");
    
        return responseDTO;
    }

    // private PostResponseDTO fetchPostFromDatabase(Long postId) {
    //     NewsPostModel post = postRepository.findById(postId)
    //         .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
    //     return modelMapper.map(post, PostResponseDTO.class);
    // }

    private PostResponseDTO convertToDTO(NewsPostModel postModel) {
        PostResponseDTO responseDTO = modelMapper.map(postModel, PostResponseDTO.class);
        responseDTO.setMessage("Successfully fetched " + postModel.getPostId());
        return responseDTO;
    }
}
