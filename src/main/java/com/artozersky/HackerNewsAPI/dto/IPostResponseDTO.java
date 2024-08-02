package com.artozersky.HackerNewsAPI.dto;

public interface IPostResponseDTO {
    Long getPostId();
    String getUrl();
    String getTitle();
    String getPostedBy();
    Double getScore();
    String getMessage();
    
    void setPostId(Long postId);
    void setUrl(String url);
    void setTitle(String title);
    void setPostedBy(String postedBy);
    void setScore(Double score);
    void setMessage(String message);
}




