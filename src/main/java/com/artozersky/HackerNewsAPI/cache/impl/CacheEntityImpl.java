package com.artozersky.HackerNewsAPI.cache.impl;

import com.artozersky.HackerNewsAPI.cache.CacheEntity;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Implementation of the CacheEntity interface that provides a simple cache mechanism.
 */
public class CacheEntityImpl implements CacheEntity {

    private final int maxSize;
    private final PriorityBlockingQueue<NewsPostModelImpl> cache;

    /**
     * Constructs a CacheEntityImpl with a specified maximum size.
     *
     * @param maxSize the maximum number of entries the cache can hold
     */
    public CacheEntityImpl(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new PriorityBlockingQueue<>(maxSize, Comparator.comparingDouble(NewsPostModelImpl::getScore));
    }

    @Override
    public NewsPostModelImpl get(Long key) {
        return cache.stream()
                .filter(post -> post.getPostId().equals(key))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<NewsPostModelImpl> getAllPosts() {
        return new ArrayList<>(cache);
    }

    @Override
    public void put(Long key, NewsPostModelImpl value) {
        if (cache.size() >= maxSize) {
            cache.poll(); // Remove the lowest priority element if the max size is reached
        }
        cache.offer(value); // Add the new item to the queue
    }

    @Override
    public void putAllPosts(List<NewsPostModelImpl> allPosts) {
        for (NewsPostModelImpl post : allPosts) {
            Long key = post.getPostId();
            put(post.getPostId(), post);
        }
    }

    @Override
    public void evict(Long key) {
        cache.removeIf(post -> post.getPostId().equals(key));
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }
}
