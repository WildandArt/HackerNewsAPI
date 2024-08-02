package com.artozersky.HackerNewsAPI.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CacheServiceImpl<Long, NewsPostModel> cacheService;
    
    @Override
    public PostResponseDTO deletePost(Long postId) {
        
        NewsPostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
        
        postRepository.delete(post);

        PostResponseDTO responseDTO = new PostResponseDTO();
        responseDTO.setPostId(postId);
        responseDTO.setMessage("Post deleted successfully");

        return responseDTO;
    }

    @Override
    public PostResponseDTO getPostById(Long postId) {
        
        NewsPostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + postId));
        
        PostResponseDTO responseDTO = modelMapper.map(post, PostResponseDTO.class);

        return responseDTO;
    }
    
    @Override
    public List<NewsPostModel> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<NewsPostModel> getSortedPostsByScore() {
        return postRepository.findAllByOrderByScoreDesc();
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

        return responseDTO;
    }

    //             //TODO: CHECK if @Preupdate of elapsed time will work here
    @Override
    public PostResponseDTO upVote(Long id) {
        NewsPostModel post = postRepository.findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.upVote();        
        post.updateScore();
        NewsPostModel updatedPost = postRepository.save(post);
        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);
        responseDTO.setMessage("Vote updated successfully");
        return responseDTO;
    }

    @Override
    public PostResponseDTO downVote(Long id) {
        NewsPostModel post = postRepository.findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Post not found with id: " + id));
        post.downVote();        
        post.updateScore();
        NewsPostModel updatedPost = postRepository.save(post);
        PostResponseDTO responseDTO = modelMapper.map(updatedPost, PostResponseDTO.class);
        responseDTO.setMessage("Vote updated successfully");
        return responseDTO;
    }



}
