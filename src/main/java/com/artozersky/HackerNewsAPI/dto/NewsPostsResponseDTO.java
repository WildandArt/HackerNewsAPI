package com.artozersky.HackerNewsAPI.dto;

import java.time.LocalDateTime;


public interface NewsPostsResponseDTO {

    /**
     * @brief Gets the time elapsed since the post was created.
     * 
     * @return The time elapsed in an integer format.
     */
    public Integer getTimeElapsed();

    /**
     * @brief Sets the time elapsed since the post was created.
     * 
     * @param timeElapsed The time elapsed to set.
     */
    public void setTimeElapsed(Integer timeElapsed);

    /**
     * @brief Gets the current number of votes on the post.
     * 
     * @return The current number of votes.
     */
    Integer getCurrentVotes();

    /**
     * @brief Sets the current number of votes on the post.
     * 
     * @param currentVotes The current number of votes to set.
     */
    void setCurrentVotes(Integer currentVotes);

    /**
     * @brief Gets the ID of the post.
     * 
     * @return The ID of the post.
     */
    Long getPostId();

    /**
     * @brief Sets the ID of the post.
     * 
     * @param postId The ID to set for the post.
     */
    void setPostId(Long postId);

    /**
     * @brief Gets the URL of the post.
     * 
     * @return The URL of the post.
     */
    String getUrl();

    /**
     * @brief Sets the URL of the post.
     * 
     * @param url The URL to set for the post.
     */
    void setUrl(String url);

    /**
     * @brief Gets the title of the post.
     * 
     * @return The title of the post.
     */
    String getTitle();

    /**
     * @brief Sets the title of the post.
     * 
     * @param title The title to set for the post.
     */
    void setTitle(String title);

    /**
     * @brief Gets the author who posted the post.
     * 
     * @return The author of the post.
     */
    String getPostedBy();

    /**
     * @brief Sets the author of the post.
     * 
     * @param postedBy The author to set for the post.
     */
    void setPostedBy(String postedBy);

    // /**
    //  * @brief Gets the score of the post.
    //  * 
    //  * @return The score of the post.
    //  */
    // Double getScore();

    // /**
    //  * @brief Sets the score of the post.
    //  * 
    //  * @param score The score to set for the post.
    //  */
    // void setScore(Double score);

    /**
     * @brief Gets the message related to the post action.
     * 
     * @return The message related to the post action.
     */
    String getMessage();

    /**
     * @brief Sets the message related to the post action.
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
