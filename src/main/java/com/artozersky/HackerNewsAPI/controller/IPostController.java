package com.artozersky.HackerNewsAPI.controller;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Interface for PostController that defines the API endpoints for managing posts.
 */
public interface IPostController {

    /**
     * Creates a new post.
     *
     * @param postCreateDTO The data transfer object containing the post creation details.
     * @return A ResponseEntity containing the created PostResponseDTO and HTTP status.
     */
    ResponseEntity<PostResponseDTO> createPost(PostCreateDTO postCreateDTO);

    /**
     * Retrieves all posts.
     *
     * @return A ResponseEntity containing a list of PostResponseDTOs and HTTP status.
     */
    ResponseEntity<List<PostResponseDTO>> getAllPosts();

    /**
     * Retrieves all posts sorted by score in descending order.
     *
     * @return A ResponseEntity containing a list of sorted PostResponseDTOs and HTTP status.
     */
    ResponseEntity<List<PostResponseDTO>> getAllSortedPosts();

    /**
     * Updates an existing post.
     *
     * @param id The ID of the post to update.
     * @param postUpdateDTO The data transfer object containing the post update details.
     * @return A ResponseEntity containing the updated PostResponseDTO and HTTP status.
     */
    ResponseEntity<PostResponseDTO> updatePost(Long id, PostUpdateDTO postUpdateDTO);

    /**
     * Upvotes a post by incrementing its vote count.
     *
     * @param id The ID of the post to upvote.
     * @return A ResponseEntity containing the updated PostResponseDTO and HTTP status.
     */
    ResponseEntity<PostResponseDTO> upvotePost(Long id);

    /**
     * Downvotes a post by decrementing its vote count.
     *
     * @param id The ID of the post to downvote.
     * @return A ResponseEntity containing the updated PostResponseDTO and HTTP status.
     */
    ResponseEntity<PostResponseDTO> downVotePost(Long id);


    ResponseEntity<PostResponseDTO> deletePost(Long id);

    ResponseEntity<PostResponseDTO> getPost(Long id);
}
