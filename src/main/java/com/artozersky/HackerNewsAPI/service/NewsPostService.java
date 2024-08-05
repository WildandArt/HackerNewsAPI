package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsCreateDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsUpdateDTOImpl;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

import java.util.List;

/**
 * Service interface for managing news posts.
 */
public interface NewsPostService {

    /**
     * Retrieves all posts.
     *
     * @return A list of all {@link NewsPostModelImpl} objects.
     */
    List<NewsPostsResponseDTOImpl> getAllPosts();

    /**
     * Retrieves all posts sorted by their score in descending order.
     *
     * @return A list of {@link NewsPostModelImpl} objects sorted by score in descending order.
     */
    List<NewsPostsResponseDTOImpl> getSortedPostsByScore();

    /**
     * Saves a new post.
     *
     * @param postCreateDTO Data Transfer Object containing the details of the post to be created.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the newly created post.
     */
    NewsPostsResponseDTOImpl savePost(NewsPostsCreateDTOImpl postCreateDTO);

    /**
     * Updates an existing post.
     *
     * @param postUpdateDTO Data Transfer Object containing the updated details of the post.
     * @param postId The ID of the post to be updated.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the updated post.
     */
    NewsPostsResponseDTOImpl updatePost(NewsPostsUpdateDTOImpl postUpdateDTO, Long postId);

    /**
     * Adds to the vote count for a post.
     *
     * @param id The ID of the post to be updated.
     * @param byNum The number to adjust the vote count by. Must be either 1 (upvote) or -1 (downvote).
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the post after the vote update.
     */
    public NewsPostsResponseDTOImpl upVote(Long id);

      /**
     * Subtracts 1 from the vote count for a post.
     *
     * @param id The ID of the post to be updated.
     * @param byNum The number to adjust the vote count by. Must be either 1 (upvote) or -1 (downvote).
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the post after the vote update.
     */
    public NewsPostsResponseDTOImpl downVote(Long id);

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to be deleted.
     * @return A {@link NewsPostsResponseDTOImpl} confirming the deletion of the post.
     */
    NewsPostsResponseDTOImpl deletePost(Long id);

    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to be retrieved.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the retrieved post.
     */
    NewsPostsResponseDTOImpl getPostById(Long id);
}
