package com.artozersky.HackerNewsAPI.cache.impl;


import com.artozersky.HackerNewsAPI.cache.CacheEntityService;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class CacheEntityServiceImpl implements CacheEntityService {

    private final CacheEntityImpl cacheEntity;

    public CacheEntityServiceImpl(@Value("${cache.size:100}") int maxSize) {

        this.cacheEntity = new CacheEntityImpl(maxSize);

    }

    @Override
    public NewsPostModelImpl getPostById(Long postId) {

        return cacheEntity.get(postId);
    }

    @Override
    public Integer getMaxSize() {

        return cacheEntity.getMaxSize();

    }

    @Override
    public Integer getSize() {

        return cacheEntity.size();

    }

    @Override
    public List<NewsPostModelImpl> getAllPosts() {

        List<NewsPostModelImpl> cachedPosts = cacheEntity.getAllPosts();

        return cachedPosts;
    }
    
    @Override
    public NewsPostModelImpl getLowestScorePost() {

        return cacheEntity.getLowestScorePost();

    }

    @Override
    public void putPost(NewsPostModelImpl post) {

        cacheEntity.put(post.getPostId(), post);

    }

    @Override
    public void putAllPosts(List<NewsPostModelImpl> posts) {

        cacheEntity.putAllPosts(posts);

    }

    @Override
    public void evictPost(Long postId) {

        cacheEntity.evict(postId);

    }

    @Override
    public void clearCache() {

        cacheEntity.clear();

    }

}
