package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;

import java.util.List;

/**
 * Service interface for managing news posts.
 */
public interface NewsPostService {

    /**
     * Retrieves all posts.
     *
     * @return A list of all {@link NewsPostModel} objects.
     */
    List<NewsPostModel> getAllPosts();

    /**
     * Retrieves all posts sorted by their score in descending order.
     *
     * @return A list of {@link NewsPostModel} objects sorted by score in descending order.
     */
    List<NewsPostModel> getSortedPostsByScore();

    /**
     * Saves a new post.
     *
     * @param postCreateDTO Data Transfer Object containing the details of the post to be created.
     * @return A {@link PostResponseDTO} containing the details of the newly created post.
     */
    PostResponseDTO savePost(PostCreateDTO postCreateDTO);

    /**
     * Updates an existing post.
     *
     * @param postUpdateDTO Data Transfer Object containing the updated details of the post.
     * @param postId The ID of the post to be updated.
     * @return A {@link PostResponseDTO} containing the details of the updated post.
     */
    PostResponseDTO updatePost(PostUpdateDTO postUpdateDTO, Long postId);

    /**
     * Updates the vote count for a post.
     *
     * @param id The ID of the post to be updated.
     * @param byNum The number to adjust the vote count by. Must be either 1 (upvote) or -1 (downvote).
     * @return A {@link PostResponseDTO} containing the details of the post after the vote update.
     */
    PostResponseDTO updateVote(Long id, Integer byNum);

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to be deleted.
     * @return A {@link PostResponseDTO} confirming the deletion of the post.
     */
    PostResponseDTO deletePost(Long id);

    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to be retrieved.
     * @return A {@link PostResponseDTO} containing the details of the retrieved post.
     */
    PostResponseDTO getPostById(Long id);
}
