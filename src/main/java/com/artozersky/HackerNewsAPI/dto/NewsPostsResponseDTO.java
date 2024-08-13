package com.artozersky.HackerNewsAPI.dto;

import java.time.LocalDateTime;

/**
 * Interface for Post Response Data Transfer Object.
 * This interface defines the structure of the data returned by the API when
 * interacting with post resources.
 */
    public interface NewsPostsResponseDTO {

    public Integer getTimeElapsed();

    public void setTimeElapsed(Integer timeElapsed);

    Integer getCurrentVotes();

    /**
     * Gets the ID of the post.
     * 
     * @return The ID of the post.
     */
    Long getPostId();

    /**
     * Gets the URL of the post.
     * 
     * @return The URL of the post.
     */
    String getUrl();

    /**
     * Gets the title of the post.
     * 
     * @return The title of the post.
     */
    String getTitle();

    /**
     * Gets the author who posted.
     * 
     * @return The author of the post.
     */
    String getPostedBy();

    /**
     * Gets the score of the post.
     * 
     * @return The score of the post.
     */
    Double getScore();

    /**
     * Gets the message related to the post action.
     * 
     * @return The message related to the post action.
     */
    String getMessage();

    void setCurrentVotes(Integer currentVotes);

    /**
     * Sets the ID of the post.
     * 
     * @param postId The ID to set for the post.
     */
    void setPostId(Long postId);

    /**
     * Sets the URL of the post.
     * 
     * @param url The URL to set for the post.
     */
    void setUrl(String url);

    /**
     * Sets the title of the post.
     * 
     * @param title The title to set for the post.
     */
    void setTitle(String title);

    /**
     * Sets the author of the post.
     * 
     * @param postedBy The author to set for the post.
     */
    void setPostedBy(String postedBy);

    /**
     * Sets the score of the post.
     * 
     * @param score The score to set for the post.
     */
    void setScore(Double score);

    /**
     * Sets the message related to the post action.
     * 
     * @param message The message to set related to the post action.
     */
    void setMessage(String message);

    /**
     * @brief Gets the creation timestamp of the post.
     *
     * @return The timestamp when the post was created.
     */
    LocalDateTime getCreatedAt();

    /**
     * @brief Sets the creation timestamp of the post.
     *
     * @param createdAt The timestamp to set for the creation of the post.
     */
    void setCreatedAt(LocalDateTime createdAt);
}
