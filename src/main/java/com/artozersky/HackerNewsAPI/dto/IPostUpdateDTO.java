package com.artozersky.HackerNewsAPI.dto;

/**
 * Interface for Post Update Data Transfer Object.
 * This interface defines the structure of the data required for updating a post.
 */
public interface IPostUpdateDTO {

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
}
