package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.model.User;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    /* List of posts sorted by score field.*/
    public List<Post> getSortedPostsByScore() {
        return postRepository.findAllByOrderByScoreDesc();
    }
    
    public Post savePost(Post post) {
        User user = userRepository.findById(post.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        post.setCurrentVotes(0);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        double timeInHours = 0;
        post.setScore(ScoreCalculator.calculateScore(0, 0, ScoreCalculator.GRAVITY));
        post.setCreatedHoursAgo((int) timeInHours);
        return postRepository.save(post);
    }
    public static class ScoreCalculator {

        private static final double GRAVITY = 1.8;
    
        public static double calculateScore(double points, double timeInHours, double gravity) {
            return (points - 1) / Math.pow((timeInHours + 2), gravity);
        }
    }
   
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Integer currentVotes = postDetails.getCurrentVotes();
        post.setAuthor(postDetails.getAuthor());
        post.setUrl(postDetails.getUrl());
        post.setTitle(postDetails.getTitle());
        post.setCurrentVotes(currentVotes);
        /* fields that need to be recalculated at each update */
        post.setCreatedHoursAgo(0);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setScore(ScoreCalculator.calculateScore(currentVotes, 0, ScoreCalculator.GRAVITY));

        return postRepository.save(post);
    }
    // public Post getPostById(Long id) {
    //     return postRepository.findById(id).orElse(null);
    // }
    // public Post downVotePost(Long id) {
    //     Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    //     post.setDownvotes(post.getDownvotes() + 1);
    //     post.setCurrentVotes(post.getCurrentVotes() - 1);
    //     return postRepository.save(post);
    // }
    // public Post upVotePost(Long id) {
       
    // }
}

