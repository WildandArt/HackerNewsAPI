package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;

import com.artozersky.HackerNewsAPI.model.NewsPostModel;

import java.util.List;

// change the name to NewsPostService
// add delete post logic we must be able to delete, and getpostbyid
// please document the interface and every function with Doxygen comments
public interface NewsPostService {
    List<NewsPostModel> getAllPosts();
    List<NewsPostModel> getSortedPostsByScore();
    PostResponseDTO savePost(PostCreateDTO postCreateDTO);
    PostResponseDTO updatePost(PostUpdateDTO postUpdateDTO, Long postId);
    PostResponseDTO updateVote(Long id, Integer byNum);
    PostResponseDTO deletePost(Long id);
    PostResponseDTO getPostById(Long id);
}


