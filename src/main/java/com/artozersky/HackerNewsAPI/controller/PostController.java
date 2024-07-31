package com.artozersky.HackerNewsAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.service.PostService;


@RestController
@RequestMapping("/posts")
public class PostController {


    private final PostService postService;
    
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
   /*
    * Create a new Post and save it into DB. 
    If for some reason creation fails, exception is caught and presented to the user in a Browser.
    */
    @PostMapping   
    public ResponseEntity<Post> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        try {
            Post createdPost = postService.savePost(postCreateDTO);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/sorted")
    public ResponseEntity<List<Post>> getAllSortedPosts() {
        List<Post> posts = postService.getSortedPostsByScore();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody PostUpdateDTO postUpdateDTO) {
        try {
            Post updatedPost = postService.updatePost(id, postUpdateDTO);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/{id}/upvote")
    public ResponseEntity<Post> upvotePost(@PathVariable("id") Long id) {
        try {
            Post updatedPost = postService.updateVote(id, 1);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/{id}/downvote")
    public ResponseEntity<Post> downVotePost(@PathVariable("id") Long id) {
        try {
            Post updatedPost = postService.updateVote(id, -1);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
