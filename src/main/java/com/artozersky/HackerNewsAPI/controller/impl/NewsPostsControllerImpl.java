package com.artozersky.HackerNewsAPI.controller.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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

import com.artozersky.HackerNewsAPI.controller.NewsPostsPostsController;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsCreateDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsUpdateDTOImpl;
import com.artozersky.HackerNewsAPI.service.NewsPostService;

@RestController
@RequestMapping("/api/posts")
@Validated
public class NewsPostsControllerImpl implements NewsPostsPostsController{

    private final NewsPostService postService;

    private final int limit;

    
    public NewsPostsControllerImpl(NewsPostService postService, @Value("${posts.page.limit}") int limit) {
        this.postService = postService;
        //this.modelMapper = modelMapper;
        this.limit = limit;

    }

    @PostMapping("")
    @Override
    public ResponseEntity<NewsPostsResponseDTOImpl> createPost(@RequestBody @Validated NewsPostsCreateDTOImpl postCreateDTO) {
        NewsPostsResponseDTOImpl createdPost = postService.savePost(postCreateDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("")
    @Override
    public ResponseEntity<List<NewsPostsResponseDTOImpl>> getAllPosts() {

        List<NewsPostsResponseDTOImpl> posts = postService.getAllPosts(limit);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<NewsPostsResponseDTOImpl> getPost(@PathVariable("id") Long id) {
        NewsPostsResponseDTOImpl gotPost = postService.getPostById(id);
        return new ResponseEntity<>(gotPost, HttpStatus.OK);
    }


    @GetMapping("/top_posts")
    @Override
    public ResponseEntity<List<NewsPostsResponseDTOImpl>> getAllSortedPosts() {

        List<NewsPostsResponseDTOImpl> posts = postService.getTopPosts(limit);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<NewsPostsResponseDTOImpl> updatePost(@PathVariable("id") Long id,
        @RequestBody @Validated NewsPostsUpdateDTOImpl postUpdateDTO) {

        NewsPostsResponseDTOImpl updatedPost = postService.updatePost(postUpdateDTO, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
}

    @PatchMapping("/{id}/upvote")
    @Override
    public ResponseEntity<NewsPostsResponseDTOImpl> upvotePost(@PathVariable("id") Long id) {

        NewsPostsResponseDTOImpl updatedPost = postService.upVote(id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
}

    @PatchMapping("/{id}/downvote")
    @Override
    public ResponseEntity<NewsPostsResponseDTOImpl> downVotePost(@PathVariable("id") Long id) {

        NewsPostsResponseDTOImpl updatedPost = postService.downVote(id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    @Override
    public ResponseEntity<NewsPostsResponseDTOImpl> deletePost(@PathVariable("id") Long id) {

        NewsPostsResponseDTOImpl deletedPost = postService.deletePost(id);
        return new ResponseEntity<>(deletedPost, HttpStatus.OK);
    }

}
