package com.artozersky.HackerNewsAPI.dto;

/**
 * Interface for Post Creation Data Transfer Object.
 * This interface defines the structure of the data required for creating a post.
 */
public interface NewsPostsCreateDTO {

    // ----------------- Getters -----------------

    /**
     * Gets the title of the post.
     * 
     * @return The title of the post.
     */
    String getTitle();

    /**
     * Gets the URL of the post.
     * 
     * @return The URL of the post.
     */
    String getUrl();

    /**
     * Gets the author who is creating the post.
     * 
     * @return The author of the post.
     */
    String getPostedBy();

    // ----------------- Setters -----------------

    /**
     * Sets the title of the post.
     * 
     * @param title The title to set for the post.
     */
    void setTitle(String title);

    /**
     * Sets the URL of the post.
     * 
     * @param url The URL to set for the post.
     */
    void setUrl(String url);

    /**
     * Sets the author of the post.
     * 
     * @param postedBy The author to set for the post.
     */
    void setPostedBy(String postedBy);
}
