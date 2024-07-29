package com.artozersky.HackerNewsAPI.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String author;
    private String url;
    private String title;
    private Integer currentVotes;
    private Timestamp createdAt;
    private Double score;
    private Integer createdHoursAgo;

    public Long getPostId() {
        return postId;
    }
    // public void setPostId(Long postId) {
    //     this.postId = postId;
    // }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getCurrentVotes() {
        return currentVotes;
    }

    public void setCurrentVotes(Integer currentVotes) {
        this.currentVotes = currentVotes;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setScore(Double score)
    {
       this.score = score; 
    }
    public Double getScore() {
        return this.score;
    } 
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreatedHoursAgo(Integer createdHoursAgo) {
        this.createdHoursAgo = createdHoursAgo;
    }
    public Integer getCreatedHoursago() {
        return this.createdHoursAgo;
    }
}
