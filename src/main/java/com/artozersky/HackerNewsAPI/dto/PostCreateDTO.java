package com.artozersky.HackerNewsAPI.dto;


/* create interfaces for both files
@NotBlank(
@Column(
@Size(
@URL(
*/
public class PostCreateDTO {
    private String title;
    private String url;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
