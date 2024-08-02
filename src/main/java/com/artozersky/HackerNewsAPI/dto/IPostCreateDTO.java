package com.artozersky.HackerNewsAPI.dto;

public interface IPostCreateDTO {

    String getTitle();
    void setTitle(String title);

    String getUrl();
    void setUrl(String url);

    String getPostedBy();
    void setPostedBy(String postedBy);
}
