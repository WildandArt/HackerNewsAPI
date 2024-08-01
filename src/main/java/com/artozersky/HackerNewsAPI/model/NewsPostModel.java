package com.artozersky.HackerNewsAPI.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


// do an interface with doxygen
/* read about and implement here
@NotBlank
@Size
@URL
@CreatedDate
@Min
@Max
@PrePersist
@PreUpdate
@Column(name = 'name', updatable =)
*/ 
/*fix author, add proper methods, including scor calculation */
@Entity
@Table(name = "posts")
public class NewsPostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author name should not exceed 100 characters")
    private String author;

    @NotBlank(message = "URL is required")
    @URL(message = "URL should be valid")
    @Size(max = 2048, message = "URL should not exceed 2048 characters")
    @Column(name = "url", length = 2048)
    private String url;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
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
    private String postedBy;

    // Getters
    public Long getPostId() {
        return postId;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCurrentVotes() {
        return currentVotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Double getScore() {
        return score;
    }

    public Integer getTimeElapsed() {
        return timeElapsed;
    }

    public String getPostedBy() {
        return postedBy;
    }

    // Setters
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCurrentVotes(Integer currentVotes) {
        this.currentVotes = currentVotes;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setTimeElapsed(Integer timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.timeElapsed = (int) java.time.Duration.between(this.createdAt, LocalDateTime.now()).toHours();
    }
    
    // dont create a new impl and interface for this.
    public static class ScoreCalculator {

        private static final double GRAVITY = 1.8;

        public static double calculateScore(double points, double timeInHours, double gravity) {
            return (points - 1) / Math.pow((timeInHours + 2), gravity);
        }
    }

    

}



