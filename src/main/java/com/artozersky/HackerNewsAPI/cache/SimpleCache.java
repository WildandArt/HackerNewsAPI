package com.artozersky.HackerNewsAPI.cache;

import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class SimpleCache {

    private final int maxSize;
    private final Map<Long, NewsPostModel> cache;

    public SimpleCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<Long, NewsPostModel>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, NewsPostModel> eldest) {
                // Remove the eldest entry if the size of the map exceeds the maxSize
                return size() > SimpleCache.this.maxSize;
            }
        };
    }

    public NewsPostModel get(Long key) {
        return cache.get(key);
    }

    public List<NewsPostModel> getAll() {
        return new ArrayList<NewsPostModel>(cache.values());
    }

    public void put(Long key, NewsPostModel value) {
        cache.put(key, value);
    }

    public void putAll(List<NewsPostModel> allPosts) {
        for (NewsPostModel post : allPosts) {
            Long key = post.getPostId();
            cache.put(key, post);
        }
    }

    public void evict(Long key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }
}
