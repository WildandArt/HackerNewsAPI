package com.artozersky.HackerNewsAPI.service.impl;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;


// import org.hibernate.engine.internal.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

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

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class NewsPostServiceImpl implements NewsPostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CacheEntityServiceImpl cacheService;

    private Integer limit;

    private final Integer cacheSize;

    private static final Logger logger = LoggerFactory.getLogger(NewsPostServiceImpl.class);

    @Autowired
    public NewsPostServiceImpl(@Value("${cache.size:100}") Integer cacheSize, @Value("${posts.page.limit:400}") Integer limit) {
        this.cacheSize = cacheSize;
        this.limit = limit;
        this.cacheService = new CacheEntityServiceImpl(cacheSize);
    }
    
    @Override
    public  NewsPostsResponseDTOImpl getPostById(Long postId) {

        NewsPostModelImpl cachedValue = cacheService.getPostById(postId);

        if (cachedValue != null) {
            return convertToDTO(cachedValue);
        }

        NewsPostModelImpl postModel = postRepository.findById(postId)
            .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));

        return convertToDTO(postModel);
    }

    @Override
    public List<NewsPostsResponseDTOImpl> getAllPosts(Integer limit) {

        if (limit <= 0) {
            throw new CustomServiceException("Limit must be greater than 0");
        }

        Pageable pageable = PageRequest.of(0, limit);
        Page<NewsPostModelImpl> page = postRepository.findAll(pageable);

        return page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsPostsResponseDTOImpl> getTopPosts(Integer limit) {

        List<NewsPostModelImpl> cachedPosts = cacheService.getAllPosts();

        int postsNeeded = limit - cachedPosts.size();

        logger.info("cached posts size: " + cachedPosts.size() );

        logger.info("posts needed: " + postsNeeded );

        List<NewsPostModelImpl> dbPosts = new ArrayList<>();

        dbPosts = fetchPostsFromDbIfNeeded(postsNeeded, cachedPosts);

        logger.info("fetched from db: " + dbPosts.size() );

        List<NewsPostModelImpl> combinedPosts = new ArrayList<>(cachedPosts);
        combinedPosts.addAll(dbPosts);

        return combinedPosts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    
    @Override
    public NewsPostsResponseDTOImpl savePost(@Valid NewsPostsCreateDTOImpl postCreateDTO) {
        
        NewsPostModelImpl post = modelMapper.map(postCreateDTO, NewsPostModelImpl.class);
        
        post.initialize();
        
        NewsPostModelImpl savedPost = postRepository.save(post);

        cacheService.putPost(savedPost);
        
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
        
        cacheService.putPost(updatedPost);

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
        
        cacheService.putPost(updatedPost);

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

        // Check if the post is in the cache
        if (cacheService.getPostById(id) != null) {
            cacheService.evictPost(id); // Remove it from the cache first
        }

        // Try to put the updated post back in the cache if it still qualifies as a top post
        cacheService.putPost(updatedPost);

        // Check if the updated post is now the lowest-scoring post in the cache
        NewsPostModelImpl lowestPostInCache = cacheService.getLowestScorePost();

        if (lowestPostInCache != null && lowestPostInCache.getPostId().equals(updatedPost.getPostId())) {
        // If it is, check if there's a higher-scoring post in the database that isn't in the cache
        List<Long> excludedIds = cacheService.getAllPosts().stream()
                                .map(NewsPostModelImpl::getPostId)
                                .collect(Collectors.toList());

        List<NewsPostModelImpl> nextHighestPosts = postRepository.findTopPostByScoreExcludingIds(excludedIds);

        if (!nextHighestPosts.isEmpty()) {
            NewsPostModelImpl nextHighestPost = nextHighestPosts.get(0); // Pick the first one
            
                cacheService.putPost(nextHighestPost);
            }
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

        cacheService.evictPost(postId);

        NewsPostModelImpl nextHighestPost = fetchNextHighestPost();
        logger.info("nextHighestPost id is: " + nextHighestPost.getPostId());
        if (nextHighestPost != null) {
            cacheService.putPost(nextHighestPost);
        }

        NewsPostsResponseDTOImpl responseDTO = new NewsPostsResponseDTOImpl();
        responseDTO.setPostId(postId);
        responseDTO.setMessage("Post deleted successfully");
    
        return responseDTO;
    }

    private NewsPostModelImpl fetchNextHighestPost() {
        // Fetch the next highest-scoring post from the database that is not in the cache
        List<Long> cachedPostIds = cacheService.getAllPosts().stream()
                .map(NewsPostModelImpl::getPostId)
                .collect(Collectors.toList());

        logger.info(" inside nextHighestPost ");

        Pageable pageable = PageRequest.of(0, 1);
        List<NewsPostModelImpl> topPosts = postRepository.findTopPostsByScoreExcludingIds(cachedPostIds, pageable);

    // Return the first post in the list, or null if the list is empty
    return topPosts.isEmpty() ? null : topPosts.get(0);
    }

    private NewsPostsResponseDTOImpl convertToDTO(NewsPostModelImpl postModel) {
        NewsPostsResponseDTOImpl responseDTO = modelMapper.map(postModel, NewsPostsResponseDTOImpl.class);
        responseDTO.setMessage("Successfully fetched " + postModel.getPostId());
        return responseDTO;
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

    @Async
    @Scheduled(fixedRateString = "${db.update.interval:3600000}")  // default 3600000 milliseconds = 1 hour
    public void updateTimeElapsedAndRefreshCache() {
        // Fetch all posts from the database
        List<NewsPostModelImpl> allPosts = postRepository.findAll();

        // Update the timeElapsed field for each post
        for (NewsPostModelImpl post : allPosts) {
            Integer newTimeElapsed = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
            post.setTimeElapsed(newTimeElapsed);
            postRepository.save(post);
        }

        // Clear and refresh the cache
        cacheService.clearCache();

        List<NewsPostModelImpl> topPosts = postRepository.findTopPostsByScore(PageRequest.of(0, cacheSize));

        cacheService.putAllPosts(topPosts);
        
        logger.info("Cache updated and timeElapsed field refreshed.");
    }

}
