// package com.artozersky.HackerNewsAPI.cache;

// import java.util.concurrent.Callable;

// import org.springframework.core.ParameterizedTypeReference;

// public interface ICacheService<K, V> {
//     V get(K key, Class<V> type);
//     void put(K key, V value);
//     void evict(K key);
//     void clear();
    
//     V getFromCacheOrDb(K key, Class<V> type, Callable<V> dbFetch);
//     <T> T getFromCacheOrDb(K key, ParameterizedTypeReference<T> typeReference, Callable<T> dbFetch);
//     <T> T get(K key, ParameterizedTypeReference<T> typeReference);
// }


package com.artozersky.HackerNewsAPI.cache;

import java.util.concurrent.Callable;

import org.springframework.core.ParameterizedTypeReference;

import com.artozersky.HackerNewsAPI.exception.CacheRetrievalException;
import com.artozersky.HackerNewsAPI.exception.DatabaseFetchException;

public interface ICacheService<K, V> {

    // Retrieve a value from the cache by its key, casting to the specified type.
    V get(K key, Class<V> type);

    // Retrieve a value from the cache by its key, using ParameterizedTypeReference for generic types.
    <T> T get(K key, ParameterizedTypeReference<T> typeReference);

    // Store a value in the cache associated with the given key.
    void put(K key, V value);

    // Remove a value from the cache by its key.
    void evict(K key);

    // Clear all entries in the cache.
    void clear();

    // Retrieve a value from the cache or fetch from the database if not present, using a class type.
    V getFromCacheOrDb(K key, Class<V> type, Callable<V> dbFetch) throws CacheRetrievalException, DatabaseFetchException;

    // Retrieve a value from the cache or fetch from the database if not present, using a ParameterizedTypeReference for generic types.
    <T> T getFromCacheOrDb(K key, ParameterizedTypeReference<T> typeReference, Callable<T> dbFetch);
}
