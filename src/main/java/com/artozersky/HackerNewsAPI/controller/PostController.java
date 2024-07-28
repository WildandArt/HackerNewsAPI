package com.artozersky.HackerNewsAPI.controller;

import com.artozersky.HackerNewsAPI.dto.PostRequest;
import com.artozersky.HackerNewsAPI.dto.PostResponse;
import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        try {
            PostResponse postResponse = postService.createPost(postRequest);
            return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }
    // @GetMapping
    // public ResponseEntity<List<Post>> getAllPosts() {
    //     List<Post> posts = postService.getAllPosts();
    //     if (posts.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(posts, HttpStatus.OK);
    // }

    


    


}
