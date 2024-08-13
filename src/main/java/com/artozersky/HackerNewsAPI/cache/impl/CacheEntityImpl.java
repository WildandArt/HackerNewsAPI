package com.artozersky.HackerNewsAPI.cache.impl;

import com.artozersky.HackerNewsAPI.cache.CacheEntity;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
import com.artozersky.HackerNewsAPI.service.impl.NewsPostServiceImpl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEntityImpl implements CacheEntity {

    private final Integer maxSize;
    private final PriorityBlockingQueue<NewsPostModelImpl> cacheQueue;
    private final ConcurrentHashMap<Long, NewsPostModelImpl> cacheMap;
    private static final Logger logger = LoggerFactory.getLogger(NewsPostServiceImpl.class);



    /**
     * Constructs a CacheEntityImpl with a specified maximum size.
     *
     * @param maxSize the maximum number of entries the cache can hold
     */
    public CacheEntityImpl(Integer maxSize) {
        this.maxSize = maxSize;
        this.cacheQueue = new PriorityBlockingQueue<>(maxSize, Comparator.comparingDouble(NewsPostModelImpl::getScore));
        this.cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public NewsPostModelImpl get(Long key) {
        return cacheMap.get(key);
    }

    @Override
    public List<NewsPostModelImpl> getAllPosts() {
        List<NewsPostModelImpl> sortedPosts = new ArrayList<>(cacheQueue);
        sortedPosts.sort(Comparator.comparingDouble(NewsPostModelImpl::getScore).reversed());
        return sortedPosts;
    }

    @Override
    public NewsPostModelImpl getLowestScorePost() {
        return cacheQueue.peek(); // Returns the post with the lowest score
    }

    @Override
    public Integer getMaxSize() {
        return this.maxSize;
    }

    @Override
    public void put(Long key, NewsPostModelImpl value) {
        synchronized (this) {
            logger.info("inside put fiunction");
            // Check if the post is already in the cache
            if (cacheMap.containsKey(key)) {
                // Evict the old version of the post from both the map and the queue
                NewsPostModelImpl oldPost = cacheMap.remove(key);
                cacheQueue.remove(oldPost);
            }

            if (cacheQueue.size() < maxSize) {
                // If there's space, add the new post to both the ConcurrentHashMap and PriorityBlockingQueue
                cacheMap.put(key, value); // Add to ConcurrentHashMap
                cacheQueue.offer(value); // Add to PriorityBlockingQueue in the correct order
            } else {
                // If the cache is full, compare the new post's score with the lowest-scoring post in the cache
                NewsPostModelImpl lowestPost = cacheQueue.peek(); // Get the lowest-scoring post

                if (lowestPost != null && value.getScore() > lowestPost.getScore()) {
                    // If the new post's score is higher than the lowest-scoring post's score
                    cacheQueue.poll(); // Remove the lowest-scoring post from PriorityBlockingQueue
                    cacheMap.remove(lowestPost.getPostId()); // Remove it from ConcurrentHashMap

                    // Add the new post
                    cacheMap.put(key, value); // Add to ConcurrentHashMap
                    cacheQueue.offer(value); // Add to PriorityBlockingQueue in the correct order
                }
                // If the new post's score is lower, it is not added to the cache
            }

            logCacheContents();
        }
    }
    
    @Override
    public void putAllPosts(List<NewsPostModelImpl> allPosts) {
        for (NewsPostModelImpl post : allPosts) {
            put(post.getPostId(), post);
        }
    }
    
    @Override
    public void evict(Long key) {
        synchronized (this) {
            NewsPostModelImpl removedPost = cacheMap.remove(key);
            if (removedPost != null) {
                cacheQueue.remove(removedPost);
            }
            logCacheContents();
        }
    }
    
    @Override
    public void clear() {
        cacheQueue.clear();
        cacheMap.clear();
    }

    @Override
    public Integer size() {
        return cacheMap.size();
    }

    public void logCacheContents() {

        logger.info("Current Cache Contents:");

        // Log each entry in the PriorityBlockingQueue (ordered by score)
        logger.info("Priority Queue (ordered by score):");

        for (NewsPostModelImpl post : cacheQueue) {

            logger.info("Post ID: {}, Title: {}, Score: {}", post.getPostId(), post.getTitle(), post.getScore());

        }

        // Log each entry in the ConcurrentHashMap (unordered, by post ID)
        logger.info("ConcurrentHashMap (unordered):");

        for (Long postId : cacheMap.keySet()) {

            NewsPostModelImpl post = cacheMap.get(postId);
            logger.info("Post ID: {}, Title: {}, Score: {}", post.getPostId(), post.getTitle(), post.getScore());

        }
    }
}
