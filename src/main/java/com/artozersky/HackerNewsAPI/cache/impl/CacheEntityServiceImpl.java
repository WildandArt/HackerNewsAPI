package com.artozersky.HackerNewsAPI.cache.impl;


import com.artozersky.HackerNewsAPI.cache.CacheEntityService;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
import com.artozersky.HackerNewsAPI.service.impl.NewsPostServiceImpl;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CacheEntityServiceImpl implements CacheEntityService {

    private final CacheEntityImpl cacheEntity;
    private static final Logger logger = LoggerFactory.getLogger(NewsPostServiceImpl.class);


    @Autowired
    public CacheEntityServiceImpl(@Value("${cache.size:100}") int maxSize) {

        this.cacheEntity = new CacheEntityImpl(maxSize);

    }

    @Override
    public NewsPostModelImpl getPostById(Long postId) {

        return cacheEntity.get(postId);
    }

    @Override
    public List<NewsPostModelImpl> getAllPosts() {

        List<NewsPostModelImpl> cachedPosts = cacheEntity.getAllPosts();

        logger.info("inside getAllPostsFromCache size : " + cachedPosts.size());

        boolean isStale = cachedPosts.stream().anyMatch(post -> {
            int currentElapsedTime = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
            return Math.abs(post.getTimeElapsed() - currentElapsedTime) > 1;//more than one hour difference
        });

        if(isStale) {
           this.clearCache();
           logger.info("Cache invalidated due to stale data");
           return List.of();
        }
        return cachedPosts;
    }

    @Override
    public void putPost(NewsPostModelImpl post) {

        cacheEntity.put(post.getPostId(), post);
        logger.info("Storing in Cache: key = " + post.getPostId() + ", value = " + post);

    }

    @Override
    public void putAllPostsInCache(List<NewsPostModelImpl> posts) {

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
