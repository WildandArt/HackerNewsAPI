package com.artozersky.HackerNewsAPI.cache.impl;


import com.artozersky.HackerNewsAPI.cache.CacheEntityService;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
import com.artozersky.HackerNewsAPI.service.impl.NewsPostServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CacheEntityServiceImpl implements CacheEntityService {

    private final CacheEntityImpl cacheEntity;
    private static final Logger logger = LoggerFactory.getLogger(NewsPostServiceImpl.class);

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

        logger.info("inside getAllPostsFromCache size : " + cachedPosts.size());

        return cachedPosts;
    }
    
    @Override
    public NewsPostModelImpl getLowestScorePost() {

        return cacheEntity.getLowestScorePost();

    }

    @Override
    public void putPost(NewsPostModelImpl post) {

        cacheEntity.put(post.getPostId(), post);
        logger.info("Storing in Cache: key = " + post.getPostId() + ", value = " + post);

    }

    @Override
    public void putAllPosts(List<NewsPostModelImpl> posts) {

        cacheEntity.putAllPosts(posts);

        logger.info("putAllPostsInCache.");

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
