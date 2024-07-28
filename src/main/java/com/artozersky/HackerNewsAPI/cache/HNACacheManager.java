package com.artozersky.HackerNewsAPI.cache;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.ning.http.client.providers.netty.chmv8.ConcurrentHashMapV8;

public class HNACacheManager implements CacheManager{
    private final ConcurrentHashMapV8<String, Cache> caches = new ConcurrentHashMapV8<>();
    private final int maxSize;
    public HNACacheManager(int maxSize){
        this.maxSize = maxSize;
    }
    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name, k -> new HNACache(name, maxSize));
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }

}