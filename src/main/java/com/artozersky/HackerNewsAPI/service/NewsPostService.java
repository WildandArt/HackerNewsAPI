package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsCreateDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;
import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsUpdateDTOImpl;

import org.springframework.data.domain.Page;

public interface NewsPostService {

    /**
     * @brief Retrieves all posts with an optional limit.
     *
     * @param limit The maximum number of posts to retrieve. If null, all posts are retrieved.
     * @return A Page of all {@link NewsPostsResponseDTOImpl} objects.
     */
    Page<NewsPostsResponseDTOImpl> getAllPosts(Integer limit);

    /**
     * @brief Saves a new post.
     *
     * @param postCreateDTO Data Transfer Object containing the details of the post to be created.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the newly created post.
     */
    NewsPostsResponseDTOImpl savePost(NewsPostsCreateDTOImpl postCreateDTO);

    /**
     * @brief Updates an existing post.
     *
     * @param postUpdateDTO Data Transfer Object containing the updated details of the post.
     * @param postId The ID of the post to be updated.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the updated post.
     */
    NewsPostsResponseDTOImpl updatePost(NewsPostsUpdateDTOImpl postUpdateDTO, Long postId);

    /**
     * @brief Increments the vote count for a post.
     *
     * @param id The ID of the post to be upvoted.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the post after the vote update.
     */
    public NewsPostsResponseDTOImpl upVote(Long id);

    /**
     * @brief Decrements the vote count for a post.
     *
     * @param id The ID of the post to be downvoted.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the post after the vote update.
     */
    public NewsPostsResponseDTOImpl downVote(Long id);

    /**
     * @brief Deletes a post by its ID.
     *
     * @param id The ID of the post to be deleted.
     * @return A {@link NewsPostsResponseDTOImpl} confirming the deletion of the post.
     */
    NewsPostsResponseDTOImpl deletePost(Long id);

    /**
     * @brief Retrieves a post by its ID.
     *
     * @param id The ID of the post to be retrieved.
     * @return A {@link NewsPostsResponseDTOImpl} containing the details of the retrieved post.
     */
    NewsPostsResponseDTOImpl getPostById(Long id);

    /**
     * @brief Retrieves the top posts based on a limit.
     *
     * @param limit The maximum number of top posts to retrieve.
     * @return A Page of {@link NewsPostsResponseDTOImpl} objects representing the top posts.
     */
    // public List<NewsPostsResponseDTOImpl> getTopPosts(Integer limit);
    public Page<NewsPostsResponseDTOImpl> getTopPosts(Integer limit);

    /**
     * @brief Updates the time elapsed for posts and refreshes the cache.
     * 
     * This method is used to update the time elapsed since each post was created and to refresh the cached data
     * used by the service.
     */
    public void updateTimeElapsedAndRefreshCache();
}
