package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Post savePost(Post post) {
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setDownvotes(0);
        post.setUpvotes(0);
        post.setCurrentVotes(0);
        return postRepository.save(post);
    }
   
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }
}
