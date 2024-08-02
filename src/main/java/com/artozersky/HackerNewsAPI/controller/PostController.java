package com.artozersky.HackerNewsAPI.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

// read about @Validated
// you need to implement deletePost to be able to delete it. 
//getbyid
// create interface and document with doxygen
@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

    private final NewsPostService postService;

    @Autowired
    private ModelMapper modelMapper;

    public PostController(NewsPostService postService) {
        this.postService = postService;
    }

    /*
     * Create a new Post and save it into DB.
     * If for some reason creation fails, exception is caught and presented to the
     * user in a Browser.
     */
    @PostMapping("") // also best practise is to do @PostMapping(""), also @Validated
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody @Validated PostCreateDTO postCreateDTO) {
        PostResponseDTO createdPost = postService.savePost(postCreateDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("") // Use "" to make this endpoint more explicit
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<NewsPostModel> posts = postService.getAllPosts();

        // Convert the list of NewsPostModel to PostResponseDTO using ModelMapper
        List<PostResponseDTO> postResponseDTOs = posts.stream()
                                                    .map(post -> modelMapper.map(post, PostResponseDTO.class))
                                                    .toList();

        // Check if the list is empty and respond with NO_CONTENT if true
        if (postResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Return the list of posts with HTTP status OK
        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
}

@GetMapping("/top_posts") // Changed the mapping to "top_posts" as per the best practice
public ResponseEntity<List<PostResponseDTO>> getAllSortedPosts() {
    List<NewsPostModel> posts = postService.getSortedPostsByScore();

    // Convert the list of NewsPostModel to PostResponseDTO using ModelMapper
    List<PostResponseDTO> postResponseDTOs = posts.stream()
                                                  .map(post -> modelMapper.map(post, PostResponseDTO.class))
                                                  .toList();

    // Check if the list is empty and respond with NO_CONTENT if true
    if (postResponseDTOs.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Return the sorted list of posts with HTTP status OK
    return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
}

    // @GetMapping // also best practise is to do @PostMapping("")
    // public ResponseEntity<List<NewsPostModel>> getAllPosts() {
    //     List<NewsPostModel> posts = postService.getAllPosts(); // You need to implement response dto
    //     if (posts.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(posts, HttpStatus.OK);
    // }

    // @GetMapping("/top_posts") // rename to top_posts
    // public ResponseEntity<List<NewsPostModel>> getAllSortedPosts() {
    //     List<NewsPostModel> posts = postService.getSortedPostsByScore(); // You need to implement response dto
    //     if (posts.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(posts, HttpStatus.OK);
    // }

    // Use @PutMapping
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("id") Long id, @RequestBody @Validated PostUpdateDTO postUpdateDTO) {

        // Full replacement of the resource, need to check about votes, they should be copied
        PostResponseDTO updatedPost = postService.updatePost(postUpdateDTO, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
}

    @PatchMapping("/{id}/upvote")
    public ResponseEntity<PostResponseDTO> upvotePost(@PathVariable("id") Long id) {

        PostResponseDTO updatedPost = postService.upVote(id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
}

    @PatchMapping("/{id}/downvote")
        public ResponseEntity<PostResponseDTO> downVotePost(@PathVariable("id") Long id) {

            PostResponseDTO updatedPost = postService.downVote(id);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
// }
//     @PatchMapping("/{id}/upvote")
//     public ResponseEntity<NewsPostModel> upvotePost(@PathVariable("id") Long id) {
//         try {
//             NewsPostModel updatedPost = postService.updateVote(id, 1); // you need to remove this hard coded, and implement in
//                                                               // the model a way to do this without any number, use ++,
//                                                               // -- perators
//             return new ResponseEntity<>(updatedPost, HttpStatus.OK);
//         } catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// You need to implement response dto
//         }
//     }

//     @PatchMapping("/{id}/downvote")
//     public ResponseEntity<NewsPostModel> downVotePost(@PathVariable("id") Long id) {
//         try {
//             NewsPostModel updatedPost = postService.updateVote(id, -1);
//             return new ResponseEntity<>(updatedPost, HttpStatus.OK); // You need to implement response dto
//         } catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }
}
