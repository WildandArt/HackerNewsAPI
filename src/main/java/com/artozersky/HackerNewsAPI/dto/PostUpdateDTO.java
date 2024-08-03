package com.artozersky.HackerNewsAPI.dto;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostUpdateDTO implements IPostUpdateDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @URL(message = "URL should be valid")
    @Size(max = 2048, message = "URL should not exceed 2048 characters")
    private String url;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
