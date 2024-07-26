package com.artozersky.HackerNewsAPI.service;

import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    
}
