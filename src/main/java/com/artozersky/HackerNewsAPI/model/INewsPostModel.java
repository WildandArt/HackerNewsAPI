package com.artozersky.HackerNewsAPI.model;

import java.time.LocalDateTime;

public interface INewsPostModel {

    // ----------------- Getters -----------------

    Long getPostId();

    String getUrl();

    String getTitle();

    Integer getCurrentVotes();

    LocalDateTime getCreatedAt();

    Double getScore();

    Integer getTimeElapsed();

    String getPostedBy();

    // ----------------- Setters -----------------

    void setPostId(Long postId);

    void setUrl(String url);

    void setTitle(String title);

    void setCurrentVotes(Integer currentVotes);

    void setCreatedAt(LocalDateTime createdAt);

    void setScore(Double score);

    void setTimeElapsed(Integer timeElapsed);

    void setPostedBy(String postedBy);

    // ----------------- Other Methods -----------------

    void initialize();

    void onPostUpdate();

    void updateScore();

    void upVote();

    void downVote();
}
