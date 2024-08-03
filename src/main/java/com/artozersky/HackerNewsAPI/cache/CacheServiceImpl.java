package com.artozersky.HackerNewsAPI.cache;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class CacheServiceImpl<K, V> implements ICacheService<K, V> {

    private final CacheEntity cacheEntity;

    public CacheServiceImpl() {
        this.cacheEntity = new CacheEntity("postCache", 100);
    }

    @Override
    public V get(K key, Class<V> type) {
        return type.cast(cacheEntity.get(key));
    }

    @Override
    public void put(K key, V value) {
        cacheEntity.put(key, value);
    }

    @Override
    public void evict(K key) {
        cacheEntity.evict(key);
    }

    @Override
    public void clear() {
        cacheEntity.clear();
    }

    @Override
    public V getFromCacheOrDb(K key, Class<V> type, Callable<V> dbFetch) {
        V value = this.get(key, type);
        if (value == null) {
            try {
                // Fetch from DB if not in cache
                value = dbFetch.call();
                // Put it in cache
                this.put(key, value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch from DB", e);
            }
        }
        return value;
    }
}
