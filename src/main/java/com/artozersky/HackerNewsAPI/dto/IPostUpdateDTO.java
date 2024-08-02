package com.artozersky.HackerNewsAPI.dto;

public interface IPostUpdateDTO {

    String getTitle();
    String getUrl();
    
    void setTitle(String title);
    void setUrl(String url);
}
