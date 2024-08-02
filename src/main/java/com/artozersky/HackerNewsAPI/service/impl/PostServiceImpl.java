package com.artozersky.HackerNewsAPI.service.impl;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.NewsPostService;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

// please space one line between objects and functions.
@Service
public class PostServiceImpl implements NewsPostService {

    @Override
    public NewsPostModel deletePost(Long postId) {
        
        return null;
    }

    @Override
    public NewsPostModel getPostById(Long postId) {
        
        return null; 
    }

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<NewsPostModel> getAllPosts() {
        return postRepository.findAll();
    }

    /* List of posts sorted by score field. */
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


    // make this function exception safe.
    // think of the user updating a empty json what do do how to prevent updating.
    @Override
    public NewsPostModel updatePost(PostUpdateDTO postUpdateDTO, Long postId) { 
        NewsPostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
            post.setTitle(postUpdateDTO.getTitle());
            post.setUrl(postUpdateDTO.getUrl());

        post.onPostUpdate();
        return postRepository.save(post);
    }

    // exception safe, read how not to crash your server and send the client the
    // error.
    // think of how to restrict this function to get only 1 or -1.
    @Override
    public NewsPostModel updateVote(Long id, Integer byNum) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Integer updatedVotesNumber = post.getCurrentVotes() + byNum; // create an upvote and downvote function in the
                                                                     // schema.
        post.setCurrentVotes(updatedVotesNumber);
        /* update score field */
        Double hoursAgoDouble = TimeUtils.calculateHoursAgo(post.getCreatedAt());
        post.setScore(ScoreCalculator.calculateScore(updatedVotesNumber, hoursAgoDouble, ScoreCalculator.GRAVITY));
        /* updating the field hoursAgo */
        Integer hoursAgoInt = (int) Math.floor(hoursAgoDouble);
        post.setCreatedHoursAgo(hoursAgoInt);

        return postRepository.save(post);
    }

}
