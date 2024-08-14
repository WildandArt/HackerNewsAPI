package com.artozersky.HackerNewsAPI.dto;

public interface NewsPostsCreateDTO {

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

    /**
     * @brief Gets the author who is creating the post.
     * 
     * @return The author of the post.
     */
    String getPostedBy();

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

    /**
     * @brief Sets the author of the post.
     * 
     * @param postedBy The author to set for the post.
     */
    void setPostedBy(String postedBy);
}
