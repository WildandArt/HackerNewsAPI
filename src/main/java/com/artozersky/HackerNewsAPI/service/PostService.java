package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.Post;

import java.util.List;

// change the name to NewsPostService
// add delete post logic we must be able to delete, and getpostbyid
// please document the interface and every function with Doxygen comments
public interface PostService {
    List<Post> getAllPosts();
    List<Post> getSortedPostsByScore();
    Post savePost(PostCreateDTO postCreateDTO);
    Post updatePost(Long postId, PostUpdateDTO postUpdateDTO);
    Post updateVote(Long id, Integer byNum);
}


