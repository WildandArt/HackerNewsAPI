package com.artozersky.HackerNewsAPI.dto;

public class PostResponseDTO implements IPostResponseDTO {

    private Long postId;
    private String title;
    private String url;
    private String postedBy;
    private Double score;
    private String message;

    @Override
    public Long getPostId() {
        return postId;
    }

    @Override
    public String getTitle() {
        return title;
    
    }
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPostedBy() {
        return postedBy;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    @Override
    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}