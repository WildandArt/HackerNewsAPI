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
        post.setContent(post.getContent());
        return postRepository.save(post);
    }
   
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(postDetails.getContent());
        post.setUpvotes(postDetails.getUpvotes());
        post.setDownvotes(postDetails.getDownvotes());
        post.setCurrentVotes(postDetails.getCurrentVotes());
        return postRepository.save(post);
    }
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    public Post downVotePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setDownvotes(post.getDownvotes() + 1);
        post.setCurrentVotes(post.getCurrentVotes() - 1);
        return postRepository.save(post);
    }
    public Post upVotePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setUpvotes(post.getUpvotes() + 1);
        post.setCurrentVotes(post.getCurrentVotes() + 1);
        return postRepository.save(post);
    }
}
