package com.artozersky.HackerNewsAPI.model;

import java.time.LocalDateTime;

/**
 * Interface for the News Post Model, representing the structure and behavior of a news post.
 */
public interface INewsPostModel {

    // ----------------- Getters -----------------

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
     * Gets the current number of votes for the post.
     * 
     * @return The current number of votes.
     */
    Integer getCurrentVotes();

    /**
     * Gets the creation timestamp of the post.
     * 
     * @return The creation timestamp.
     */
    LocalDateTime getCreatedAt();

    /**
     * Gets the score of the post.
     * 
     * @return The score of the post.
     */
    Double getScore();

    /**
     * Gets the time elapsed since the post was created, in hours.
     * 
     * @return The time elapsed in hours.
     */
    Integer getTimeElapsed();

    /**
     * Gets the username of the person who posted.
     * 
     * @return The username of the poster.
     */
    String getPostedBy();

    // ----------------- Setters -----------------

    /**
     * Sets the ID of the post.
     * 
     * @param postId The ID to set.
     */
    void setPostId(Long postId);

    /**
     * Sets the URL of the post.
     * 
     * @param url The URL to set.
     */
    void setUrl(String url);

    /**
     * Sets the title of the post.
     * 
     * @param title The title to set.
     */
    void setTitle(String title);

    /**
     * Sets the current number of votes for the post.
     * 
     * @param currentVotes The number of votes to set.
     */
    void setCurrentVotes(Integer currentVotes);

    /**
     * Sets the creation timestamp of the post.
     * 
     * @param createdAt The creation timestamp to set.
     */
    void setCreatedAt(LocalDateTime createdAt);

    /**
     * Sets the score of the post.
     * 
     * @param score The score to set.
     */
    void setScore(Double score);

    /**
     * Sets the time elapsed since the post was created, in hours.
     * 
     * @param timeElapsed The time elapsed in hours to set.
     */
    void setTimeElapsed(Integer timeElapsed);

    /**
     * Sets the username of the person who posted.
     * 
     * @param postedBy The username of the poster to set.
     */
    void setPostedBy(String postedBy);

    // ----------------- Other Methods -----------------

    /**
     * Initializes the post, setting default values for fields like current votes and creation timestamp.
     */
    void initialize();

    /**
     * Updates the post's state when it is modified, such as recalculating the score.
     */
    void onPostUpdate();

    /**
     * Updates the score of the post based on its current state.
     */
    void updateScore();

    /**
     * Increases the vote count by one.
     */
    void upVote();

    /**
     * Decreases the vote count by one.
     */
    void downVote();
}
