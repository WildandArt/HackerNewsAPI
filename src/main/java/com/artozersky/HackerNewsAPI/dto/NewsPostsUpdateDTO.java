package com.artozersky.HackerNewsAPI.dto;

/**
 * @brief Interface for Post Update Data Transfer Object.
 * 
 * This interface defines the structure of the data required for updating a post.
 * It includes methods to get and set the title and URL of the post.
 */
public interface NewsPostsUpdateDTO {

    // ----------------- Getters -----------------

    /**
     * @brief Gets the title of the post.
     * 
     * @return The title of the post.
     */
    String getTitle();

    /**
     * @brief Gets the URL of the post.
     * 
     * @return The URL of the post.
     */
    String getUrl();

    // ----------------- Setters -----------------

    /**
     * @brief Sets the title of the post.
     * 
     * @param title The title to set for the post.
     */
    void setTitle(String title);

    /**
     * @brief Sets the URL of the post.
     * 
     * @param url The URL to set for the post.
     */
    void setUrl(String url);
}
