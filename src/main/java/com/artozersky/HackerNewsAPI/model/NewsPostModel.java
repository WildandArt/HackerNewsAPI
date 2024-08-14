package com.artozersky.HackerNewsAPI.model;

import java.time.LocalDateTime;


public interface NewsPostModel {

    // ----------------- Getters -----------------

    /**
     * @brief Gets the ID of the post.
     * 
     * @return The ID of the post.
     */
    Long getPostId();

    /**
     * @brief Gets the URL of the post.
     * 
     * @return The URL of the post.
     */
    String getUrl();

    /**
     * @brief Gets the title of the post.
     * 
     * @return The title of the post.
     */
    String getTitle();

    /**
     * @brief Gets the current number of votes for the post.
     * 
     * @return The current number of votes.
     */
    Integer getCurrentVotes();

    /**
     * @brief Gets the creation timestamp of the post.
     * 
     * @return The creation timestamp.
     */
    LocalDateTime getCreatedAt();

    /**
     * @brief Gets the score of the post.
     * 
     * @return The score of the post.
     */
    Double getScore();

    /**
     * @brief Gets the time elapsed since the post was created, in hours.
     * 
     * @return The time elapsed in hours.
     */
    Integer getTimeElapsed();

    /**
     * @brief Gets the username of the person who posted.
     * 
     * @return The username of the poster.
     */
    String getPostedBy();

    // ----------------- Setters -----------------

    /**
     * @brief Sets the ID of the post.
     * 
     * @param postId The ID to set.
     */
    void setPostId(Long postId);

    /**
     * @brief Sets the URL of the post.
     * 
     * @param url The URL to set.
     */
    void setUrl(String url);

    /**
     * @brief Sets the title of the post.
     * 
     * @param title The title to set.
     */
    void setTitle(String title);

    /**
     * @brief Sets the current number of votes for the post.
     * 
     * @param currentVotes The number of votes to set.
     */
    void setCurrentVotes(Integer currentVotes);

    /**
     * @brief Sets the creation timestamp of the post.
     * 
     * @param createdAt The creation timestamp to set.
     */
    void setCreatedAt(LocalDateTime createdAt);

    /**
     * @brief Sets the score of the post.
     * 
     * @param score The score to set.
     */
    void setScore(Double score);

    /**
     * @brief Sets the time elapsed since the post was created, in hours.
     * 
     * @param timeElapsed The time elapsed in hours to set.
     */
    void setTimeElapsed(Integer timeElapsed);

    /**
     * @brief Sets the username of the person who posted.
     * 
     * @param postedBy The username of the poster to set.
     */
    void setPostedBy(String postedBy);

    // ----------------- Other Methods -----------------

    /**
     * @brief Initializes the post with default values.
     * 
     * This method sets default values for fields such as current votes 
     * and creation timestamp.
     */
    void initialize();

    /**
     * @brief Updates the post's state when it is modified.
     * 
     * This method should be called whenever the post is updated, and it 
     * recalculates the score or any other necessary fields.
     */
    void onPostUpdate();

    /**
     * @brief Updates the score of the post based on its current state.
     */
    void updateScore();

    /**
     * @brief Increases the vote count by one.
     */
    void upVote();

    /**
     * @brief Decreases the vote count by one.
     */
    void downVote();
}
