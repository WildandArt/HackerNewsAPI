package com.artozersky.HackerNewsAPI.service.impl;

import java.util.List;
import java.util.ArrayList;


// import org.hibernate.engine.internal.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityServiceImpl;
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
import java.util.Collections;
import jakarta.validation.Valid;

@Service
public class NewsPostServiceImpl implements NewsPostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CacheEntityServiceImpl cacheService;

    private final Integer getRequestLimit = 400;

    private final int cacheSize;

    @Autowired
    public NewsPostServiceImpl(@Value("${cache.size:100}") int cacheSize) {
        this.cacheSize = cacheSize;
        this.cacheService = new CacheEntityServiceImpl(cacheSize);
    }
    
    @Override
    public  NewsPostsResponseDTOImpl getPostById(Long postId) {

        NewsPostModelImpl cachedValue = cacheService.getPostFromCacheById(postId);

        if (cachedValue != null) {
            return convertToDTO(cachedValue);
        }

        NewsPostModelImpl postModel = postRepository.findById(postId)
            .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));

        cacheService.putPostInCache(postModel);

        return convertToDTO(postModel);
    }
/* getAllPosts
 * 
 * what? How? Why?
 * purpose: to get all posts. getall you can from cache. 
 * By the way is there something at all? whatever is there just grab it and the remaining grab from db.
 * lets say there is a limit and its 400.
 *  List<NewsPostModelImpl> cachedPosts = cacheservice.getAllNotStalePostsFromCache();
 * if null then -> pull from db -> update cache 
 * else take from db the remaining (limit - cache.size) after cache.size posts.
 * if there is nothing left to take dont, if there is nothing in cache take it all.
 * if there is something to take, then you have to take from post_id = cache.size() + 1
 *  how to grab from db 20ieth post to 40ieth after sorting them????
 */
    @Override
    public List<NewsPostsResponseDTOImpl> getAllPosts() {

        // Step 1: Try to get the list of NewsPostModel from the cache
        List<NewsPostModelImpl> cachedPosts = cacheService.getAllPostsFromCache();

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
                cacheService.clearCache();
                System.out.println("Cache invalidated due to stale data");
            }
        }
        // Step 2: Fetch the list of NewsPostModel from the database if not in cache
        List<NewsPostModelImpl> allPosts = postRepository.findAll();

        // Step 3: Store the list in the cache
        cacheService.putAllPostsInCache(allPosts);
        System.out.println("Storing in Cache:  value = " + allPosts);

        // Step 4: Convert to PostResponseDTO and return
        return allPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
/////
    @Override

    public List<NewsPostsResponseDTOImpl> getTopPosts(int limit) {

    List<NewsPostModelImpl> cachedPosts = cacheService.getAllPostsFromCache();//stale will be checked inside

    int postsNeeded = limit - cachedPosts.size();

    List<NewsPostModelImpl> dbPosts = new ArrayList<>();

    dbPosts = fetchPostsFromDbIfNeeded(postsNeeded, cachedPosts);

    List<NewsPostModelImpl> combinedPosts = new ArrayList<>(cachedPosts);
    combinedPosts.addAll(dbPosts);

    return combinedPosts.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
}

public List<NewsPostModelImpl> fetchPostsFromDbIfNeeded(
    int postsNeeded, 
    List<NewsPostModelImpl> cachedPosts) {

    if (postsNeeded > 0) {
        Pageable pageable = PageRequest.of(0, postsNeeded);
        List<NewsPostModelImpl> dbPosts = postRepository.findTopPostsByScoreExcludingIds(
            cachedPosts.stream()
                .map(NewsPostModelImpl::getPostId)
                .collect(Collectors.toList()), 
            pageable);

            //updating this field each time called to db
            dbPosts.forEach(NewsPostModelImpl::updateElapsedTime);
            return dbPosts;
    }
    return new ArrayList<>();
}

public void updateCacheWithTopPosts() {
    List<NewsPostModelImpl> topPosts = postRepository.findTopPostsByScore(PageRequest.of(0, cacheSize));

    cacheService.clearCache(); // Clear the existing cache before updating
    cacheService.putAllPostsInCache(topPosts);

    System.out.println("Cache updated with top " + cacheSize + " posts by score.");
}

//function that will fetch  top posts by score in size of cache.size and update the cache

/////
    @Override
    public List<NewsPostsResponseDTOImpl> getSortedPostsByScore() {
        // Step 1: Try to get the list of NewsPostModel from the cache
        List<NewsPostModelImpl> cachedPosts = cacheService.getAllPostsFromCache();

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
                cacheService.clearCache();
                System.out.println("Cache invalidated due to stale data");
            }
        }

        // Step 2: Fetch the list of NewsPostModel from the database if not in cache or if cache is stale
        List<NewsPostModelImpl> allPosts = postRepository.findAllByOrderByScoreDesc();

        // Step 3: Store the list in the cache
        cacheService.putAllPostsInCache(allPosts);
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
        //add cache invalidation here ,if post changes the top40 it should be treated
        
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
        if (cacheService.getPostFromCacheById(postId) != null) {
            cacheService.putPostInCache(updatedPost);
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
        if (cacheService.getPostFromCacheById(id) != null) {
            cacheService.putPostInCache(updatedPost);
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
        if (cacheService.getPostFromCacheById(id) != null) {
            cacheService.putPostInCache(updatedPost);
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

        cacheService.evictPost(postId);//from cache
    
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
