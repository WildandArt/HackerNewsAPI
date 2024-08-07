package com.artozersky.HackerNewsAPI.cache.impl;


import com.artozersky.HackerNewsAPI.cache.CacheEntityService;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheEntityServiceImpl implements CacheEntityService {

    private final CacheEntityImpl cacheEntity;

    @Autowired
    public CacheEntityServiceImpl(@Value("${cache.size:100}") int maxSize) {

        this.cacheEntity = new CacheEntityImpl(maxSize);

    }

    @Override
    public NewsPostModelImpl getPostFromCacheById(Long postId) {

        return cacheEntity.get(postId);
    }

    @Override
    public List<NewsPostModelImpl> getAllPostsFromCache() {//posts that are not stale, if stale??

        List<NewsPostModelImpl> cachedPosts = cacheEntity.getAll();

        boolean isStale = cachedPosts.stream().anyMatch(post -> {
            int currentElapsedTime = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
            return Math.abs(post.getTimeElapsed() - currentElapsedTime) > 1;//more than one hour difference
        });

        if(isStale) {
           this.clearCache();
           System.out.println("Cache invalidated due to stale data");
        }
        return cachedPosts;
    }


    // if (!cachedPosts.isEmpty() && (cachedPosts.size() >= postRepository.count())) {

    //     boolean isStale = cachedPosts.stream().anyMatch(post -> {
    //         int currentElapsedTime = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
    //         return Math.abs(post.getTimeElapsed() - currentElapsedTime) > 1;//more than one hou difference
    //     });
    //     if(!isStale) {
    //         System.out.println("Cache hit: Retrieved all posts from Cache");
    //         return cachedPosts.stream()
    //                 .map(this::convertToDTO)
    //                 .collect(Collectors.toList());
    //     }
    //     else{
    //         cacheService.clearCache();
    //         System.out.println("Cache invalidated due to stale data");
    //     }
    // }


    @Override
    public void putPostInCache(NewsPostModelImpl post) {

        cacheEntity.put(post.getPostId(), post);
        System.out.println("Storing in Cache: key = " + post.getPostId() + ", value = " + post);

    }

    @Override
    public void putAllPostsInCache(List<NewsPostModelImpl> posts) {

        cacheEntity.putAll(posts);

        System.out.println("putAllPostsInCache.");

    }

    @Override
    public void evictPost(Long postId) {

        cacheEntity.evict(postId);

    }

    @Override
    public void clearCache() {

        cacheEntity.clear();

    }
    @Override
    public boolean checkIfStale(List<NewsPostModelImpl> cachedPosts) {
    
        return false;

    }


}