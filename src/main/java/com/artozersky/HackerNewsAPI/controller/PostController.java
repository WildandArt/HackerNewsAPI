package com.artozersky.HackerNewsAPI.controller;

import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private PostService postService;
   /*
    * The creation of a new post is straightforward. Create a new Post and save it into DB. 
    If for some reason creation fails, exception is caught and presented to the user in a Browser.
    */
    @PostMapping   
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            // System.out.println("userId: " + userId);  // Log the userId
            // System.out.println("Post: " + post);   
            Post createdPost = postService.savePost(post);
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

    // @PutMapping("/{id}")
    // public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
    //     try {
    //         Post updatedPost = postService.updatePost(id, post);
    //         return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    // @PutMapping("/{id}")
    // public ResponseEntity<Post> upvotePost(@PathVariable("id") Long id, @RequestBody Post post) {
    //     return new ResponseEntity<>(null, HttpStatus.OK);
    // }
    // @PutMapping("/{id}")
    // public ResponseEntity<Post> downVotePost(@PathVariable("id") Long id, @RequestBody Post post) {
    //     return new ResponseEntity<>(null, HttpStatus.OK);
    // }
}
