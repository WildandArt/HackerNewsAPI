package com.artozersky.HackerNewsAPI.model.impl;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import com.artozersky.HackerNewsAPI.model.NewsPostModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Entity
@Table(name = "posts", indexes = {
    @Index(name = "idx_score", columnList = "score")
})
public class NewsPostModelImpl implements NewsPostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "URL is required")
    @URL(message = "URL should be valid")
    @Size(max = 2048, message = "URL should not exceed 2048 characters")
    @Column(name = "url", length = 2048)
    private String url;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "current_votes")
    private Integer currentVotes;

    @Column(name = "created_at", updatable = true)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "score")
    private Double score;

    @Column(name = "time_elapsed")
    private Integer timeElapsed;

    @NotBlank(message = "Posted by is required")
    @Size(max = 100, message = "Author name should not exceed 100 characters")
    @Column(name = "posted_by", length = 100)
    private String postedBy;

    // ----------------- Getters -----------------

    @Override
    public Long getPostId() {
        return postId;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getCurrentVotes() {
        return currentVotes;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public Integer getTimeElapsed() {
        return timeElapsed;
    }

    @Override
    public String getPostedBy() {
        return postedBy;
    }

    // ----------------- Setters -----------------

    @Override
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setCurrentVotes(Integer currentVotes) {
        this.currentVotes = currentVotes;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public void setTimeElapsed(Integer timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    @Override
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    // ----------------- Other Methods -----------------
    @Override
    public void initialize() {
        this.setCurrentVotes(0);
        this.setTimeElapsed(0);
        this.setCreatedAt(LocalDateTime.now());
        updateScore();
    }

    @Override
    public void onPostUpdate() {
        this.setCreatedAt(LocalDateTime.now());
        this.setTimeElapsed(0);
        this.updateScore();
    }
    @PrePersist
    protected void createdAtOnCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateElapsedTime() {
        this.timeElapsed = (int) java.time.Duration.between(this.createdAt, LocalDateTime.now()).toHours();
    }

    @Override
    public void updateScore() {
        final double GRAVITY = 1.8;
        double updatedScore = (this.getCurrentVotes() - 1) / Math.pow((this.getTimeElapsed() + 2), GRAVITY);
        this.setScore(updatedScore);
    }

    @Override
    public void upVote() {
        this.currentVotes++;
        this.updateScore();
    }

    @Override
    public void downVote() {
        this.currentVotes--;
        this.updateScore();
    }
    
}

