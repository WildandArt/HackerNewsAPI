package com.artozersky.HackerNewsAPI.cache;

import org.springframework.core.ParameterizedTypeReference;
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
    @SuppressWarnings("unchecked")
    public <T> T get(K key, ParameterizedTypeReference<T> typeReference) {
        Object cachedValue = cacheEntity.get(key);
        return (T) cachedValue;
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
                value = dbFetch.call();
                this.put(key, value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch from DB", e);
            }
        }
        return value;
    }

    @Override
    public <T> T getFromCacheOrDb(K key, ParameterizedTypeReference<T> typeReference, Callable<T> dbFetch) {
        T value = this.get(key, typeReference);
        if (value == null) {
            try {
                value = dbFetch.call();
                this.put(key, (V) value);  // Cast and store the fetched value
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch from DB", e);
            }
        }
        return value;
    }
}
