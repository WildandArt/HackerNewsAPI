package com.artozersky.HackerNewsAPI.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.service.NewsPostService;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController implements IPostController{

    private final NewsPostService postService;

    private final ModelMapper modelMapper;
    
    public PostController(NewsPostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    @Override
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody @Validated PostCreateDTO postCreateDTO) {
        PostResponseDTO createdPost = postService.savePost(postCreateDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("")
    @Override
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {

        List<NewsPostModel> posts = postService.getAllPosts();

        List<PostResponseDTO> postResponseDTOs = posts.stream()
                                                    .map(post -> modelMapper.map(post, PostResponseDTO.class))
                                                    .toList();

        if (postResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable("id") Long id) {
        PostResponseDTO gotPost = postService.getPostById(id);
        return new ResponseEntity<>(gotPost, HttpStatus.OK);
    }


    @GetMapping("/top_posts")
    @Override
    public ResponseEntity<List<PostResponseDTO>> getAllSortedPosts() {

        List<NewsPostModel> posts = postService.getSortedPostsByScore();

        List<PostResponseDTO> postResponseDTOs = posts.stream()
                                                    .map(post -> modelMapper.map(post, PostResponseDTO.class))
                                                    .toList();

        if (postResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("id") Long id, @RequestBody @Validated PostUpdateDTO postUpdateDTO) {

        // Full replacement of the resource, need to check about votes, they should be copied
        PostResponseDTO updatedPost = postService.updatePost(postUpdateDTO, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
}

    @PatchMapping("/{id}/upvote")
    @Override
    public ResponseEntity<PostResponseDTO> upvotePost(@PathVariable("id") Long id) {

        PostResponseDTO updatedPost = postService.upVote(id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
}

    @PatchMapping("/{id}/downvote")
    @Override
    public ResponseEntity<PostResponseDTO> downVotePost(@PathVariable("id") Long id) {

        PostResponseDTO updatedPost = postService.downVote(id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    @Override
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable("id") Long id) {

        PostResponseDTO deletedPost = postService.deletePost(id);
        return new ResponseEntity<>(deletedPost, HttpStatus.OK);
    }

}
