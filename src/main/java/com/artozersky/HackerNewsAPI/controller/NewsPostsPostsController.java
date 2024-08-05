package com.artozersky.HackerNewsAPI.controller;

import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsCreateDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsUpdateDTOImpl;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for PostController that defines the API endpoints for managing posts.
 */
public interface NewsPostsPostsController {

    /**
     * Creates a new post.
     *
     * @param postCreateDTO The data transfer object containing the post creation details.
     * @return A ResponseEntity containing the created PostResponseDTO and HTTP status.
     */
    ResponseEntity<NewsPostsResponseDTOImpl> createPost(NewsPostsCreateDTOImpl postCreateDTO);

    /**
     * Retrieves all posts.
     *
     * @return A ResponseEntity containing a list of PostResponseDTOs and HTTP status.
     */
    ResponseEntity<List<NewsPostsResponseDTOImpl>> getAllPosts();

    /**
     * Retrieves all posts sorted by score in descending order.
     *
     * @return A ResponseEntity containing a list of sorted PostResponseDTOs and HTTP status.
     */
    ResponseEntity<List<NewsPostsResponseDTOImpl>> getAllSortedPosts();

    /**
     * Updates an existing post.
     *
     * @param id The ID of the post to update.
     * @param postUpdateDTO The data transfer object containing the post update details.
     * @return A ResponseEntity containing the updated PostResponseDTO and HTTP status.
     */
    ResponseEntity<NewsPostsResponseDTOImpl> updatePost(Long id, NewsPostsUpdateDTOImpl postUpdateDTO);

    /**
     * Upvotes a post by incrementing its vote count.
     *
     * @param id The ID of the post to upvote.
     * @return A ResponseEntity containing the updated PostResponseDTO and HTTP status.
     */
    ResponseEntity<NewsPostsResponseDTOImpl> upvotePost(Long id);

    /**
     * Downvotes a post by decrementing its vote count.
     *
     * @param id The ID of the post to downvote.
     * @return A ResponseEntity containing the updated PostResponseDTO and HTTP status.
     */
    ResponseEntity<NewsPostsResponseDTOImpl> downVotePost(Long id);


    ResponseEntity<NewsPostsResponseDTOImpl> deletePost(Long id);

    ResponseEntity<NewsPostsResponseDTOImpl> getPost(Long id);
}
