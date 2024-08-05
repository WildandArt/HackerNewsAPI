package com.artozersky.HackerNewsAPI.dto.impl;

import java.time.LocalDateTime;

import com.artozersky.HackerNewsAPI.dto.NewsPostsResponseDTO;

public class NewsPostsResponseDTOImpl implements NewsPostsResponseDTO {

    private Long postId;
    private String title;
    private String url;
    private String postedBy;
    private Double score;
    private Integer currentVotes;
    private String message;
    private LocalDateTime createdAt;


    @Override
    public Integer getCurrentVotes() {
        return currentVotes;
    }

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
    public void setCurrentVotes(Integer currentVotes) {
        this.currentVotes = currentVotes;
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
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Calculated dynamically
    @Override
    public Integer getTimeElapsed() {
        if (this.createdAt == null) {
            return null; // or return a default value like 0
        }
        return (int) java.time.Duration.between(this.createdAt, LocalDateTime.now()).toHours();
    }
}
