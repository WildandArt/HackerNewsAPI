package com.artozersky.HackerNewsAPI.controller;

import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            Post createdPost = postService.savePost(post);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        try{
            Post existingPost = postService.getPostById(id);
            if(null == existingPost){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            Post updatedPost = postService.updatePost(id, postDetails);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/downvote")
    public ResponseEntity<Post> downVote(@PathVariable Long id) {
        try{
            Post existingPost = postService.getPostById(id);
            if(null == existingPost){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            Post updatedPost = postService.downVotePost(id);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}/upvote")
    public ResponseEntity<Post> upVote(@PathVariable Long id) {
        try{
            Post existingPost = postService.getPostById(id);
            if(null == existingPost){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            Post updatedPost = postService.upVotePost(id);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    


}
