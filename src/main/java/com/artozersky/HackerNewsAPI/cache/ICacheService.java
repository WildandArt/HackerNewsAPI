package com.artozersky.HackerNewsAPI.cache;

import java.util.concurrent.Callable;

public interface ICacheService<K, V> {
    V get(K key, Class<V> type);
    void put(K key, V value);
    void evict(K key);
    void clear();
    
    V getFromCacheOrDb(K key, Class<V> type, Callable<V> dbFetch);
}