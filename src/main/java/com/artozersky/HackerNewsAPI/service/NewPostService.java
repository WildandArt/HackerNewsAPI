package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;

import java.util.List;

// change the name to NewsPostService
// add delete post logic we must be able to delete, and getpostbyid
// please document the interface and every function with Doxygen comments
public interface NewPostService {
    List<NewsPostModel> getAllPosts();
    List<NewsPostModel> getSortedPostsByScore();
    NewsPostModel savePost(PostCreateDTO postCreateDTO);
    NewsPostModel updatePost(Long postId, PostUpdateDTO postUpdateDTO);
    NewsPostModel updateVote(Long id, Integer byNum);
    NewsPostModel deletePost(Long id);
    NewsPostModel getPostById(Long id);
}


