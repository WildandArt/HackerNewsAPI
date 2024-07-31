package com.artozersky.HackerNewsAPI.dto;

/* use:
@NotBlank(
@Column(
@Size(
@URL(
*/
public class PostUpdateDTO {
    private String title;
    private String url;

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
}

