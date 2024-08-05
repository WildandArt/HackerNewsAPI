package com.artozersky.HackerNewsAPI.cache.impl;

import com.artozersky.HackerNewsAPI.cache.CacheEntity;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Implementation of the CacheEntity interface that provides a simple cache mechanism.
 */
public class CacheEntityImpl implements CacheEntity {

    private final int maxSize;
    private final Map<Long, NewsPostModelImpl> cache;

    /**
     * Constructs a CacheEntityImpl with a specified maximum size.
     *
     * @param maxSize the maximum number of entries the cache can hold
     */
    public CacheEntityImpl(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<Long, NewsPostModelImpl>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, NewsPostModelImpl> eldest) {
                // Remove the eldest entry if the size of the map exceeds the maxSize
                return size() > CacheEntityImpl.this.maxSize;
            }
        };
    }

    @Override
    public NewsPostModelImpl get(Long key) {
        return cache.get(key);
    }

    @Override
    public List<NewsPostModelImpl> getAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void put(Long key, NewsPostModelImpl value) {
        cache.put(key, value);
    }

    @Override
    public void putAll(List<NewsPostModelImpl> allPosts) {
        for (NewsPostModelImpl post : allPosts) {
            Long key = post.getPostId();
            cache.put(key, post);
        }
    }

    @Override
    public void evict(Long key) {
        cache.remove(key);
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
