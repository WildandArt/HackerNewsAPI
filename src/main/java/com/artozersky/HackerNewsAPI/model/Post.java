package com.artozersky.HackerNewsAPI.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
@Entity
@Table(name = "posts") // please add indexes to the database, think how and why and what field to index. 
public class Post { // rename this to NewsPostModel/Schema
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    // remove comments from code.
    // @ManyToOne
    // @JoinColumn(name = "user_id", nullable = false)
    @Column(name = "user_id", nullable = false) // remove user id
    private Long userId; // instead of user id i would create String postedBy

    private String author;
    private String url;
    private String title;
    @Column(name = "current_votes")
    private Integer currentVotes;
    @Column(name = "created_at")
    private Timestamp createdAt; // look at this type LocalDateTime, and insert config to .properties
    private Double score;
    @Column(name = "created_hours_ago")
    private Integer createdHoursAgo; // change the name to timeElapsed


    // Group all the setters together and group all the getters together.
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public Integer getCreatedHoursAgo() {
        return this.createdHoursAgo;
    }
}
