package com.artozersky.HackerNewsAPI.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/* create interfaces for both files
@Column(

*/
public class PostCreateDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @URL(message = "URL should be valid")
    @Size(max = 2048, message = "URL should not exceed 2048 characters")
    private String url;

    @NotBlank(message = "Posted by is required")
    @Size(max = 100, message = "Author name should not exceed 100 characters")
    private String postedBy;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
