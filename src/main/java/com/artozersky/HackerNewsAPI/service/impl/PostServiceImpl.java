package com.artozersky.HackerNewsAPI.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.artozersky.HackerNewsAPI.cache.CacheServiceImpl;
import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.exception.CustomNotFoundException;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.NewsPostService;

import jakarta.validation.Valid;

@Service
public class PostServiceImpl implements NewsPostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CacheServiceImpl<Long, PostResponseDTO> cacheService;

    @Autowired
    private CacheServiceImpl<String, List<PostResponseDTO>> allPostsCacheService;
    
    @Override
    public PostResponseDTO getPostById(Long postId) {

        PostResponseDTO post = cacheService.getFromCacheOrDb(
            postId,
            PostResponseDTO.class,
            () -> fetchPostFromDatabase(postId)
        );

        if (post.getTimeElapsed() > 1) { //more than 1 hour has passed
            post = fetchPostFromDatabase(postId);
            cacheService.put(postId, post); //refresh the cache
        }

        return post;
    }
    
    @Override
    public List<PostResponseDTO> getAllPosts() {
        String cacheKey = "all_posts";

        // Retrieve from cache, fetching from DB if necessary
        List<PostResponseDTO> posts = allPostsCacheService.getFromCacheOrDb(
            cacheKey,
            new ParameterizedTypeReference<List<PostResponseDTO>>() {},
            () -> postRepository.findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostResponseDTO.class))
                .toList()
        );

        if (posts != null && !posts.isEmpty()) {
            allPostsCacheService.put(cacheKey, posts);
        }

        return posts;
    }

    // return postRepository.findAll();
    @Override
    public List<PostResponseDTO> getSortedPostsByScore() {
        String cacheKey = "sorted_posts";

        // Retrieve from cache, fetching from DB if necessary
        List<PostResponseDTO> posts = allPostsCacheService.getFromCacheOrDb(
            cacheKey,
            new ParameterizedTypeReference<List<PostResponseDTO>>() {},
            () -> postRepository.findAllByOrderByScoreDesc()
                .stream()
                .map(post -> modelMapper.map(post, PostResponseDTO.class))
                .toList()
        );

        if (posts != null && !posts.isEmpty()) {
            allPostsCacheService.put(cacheKey, posts);
        }

        return posts;
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
        
        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);
        responseDTO.setMessage("Post updated successfully");

        //if in the cache update the cache
        if (cacheService.get(postId, PostResponseDTO.class) != null) {
            cacheService.put(postId, responseDTO);
        }
        
        return responseDTO;
    }
    
    @Override
    public PostResponseDTO upVote(Long id) {
        NewsPostModel post = postRepository.findById(id)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.upVote();        
        NewsPostModel updatedPost = postRepository.save(post);
        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);

        responseDTO.setMessage("Vote updated successfully");
        
        if (cacheService.get(id, PostResponseDTO.class) != null) {
            cacheService.put(id, responseDTO);
        }
        return responseDTO;
    }
    
    @Override
    public PostResponseDTO downVote(Long id) {
        NewsPostModel post = postRepository.findById(id)
        .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.downVote();        
        NewsPostModel updatedPost = postRepository.save(post);
        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);
        responseDTO.setMessage("Vote updated successfully");
        if (cacheService.get(id, PostResponseDTO.class) != null) {
            cacheService.put(id, responseDTO);
        }
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

    private PostResponseDTO fetchPostFromDatabase(Long postId) {
        NewsPostModel post = postRepository.findById(postId)
            .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostResponseDTO.class);
    }
}
