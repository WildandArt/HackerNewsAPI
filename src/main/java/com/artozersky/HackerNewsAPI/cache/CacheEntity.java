package com.artozersky.HackerNewsAPI.cache;

import java.util.List;

import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

/**
 * Interface for the cache entity that provides methods to interact with the cache.
 */
public interface CacheEntity {

    /**
     * Retrieves a NewsPostModel from the cache based on the given key.
     *
     * @param key the key of the cache entry
     * @return the NewsPostModel associated with the key, or null if not found
     */
    NewsPostModelImpl get(Long key);

    /**
     * Retrieves all NewsPostModels currently stored in the cache.
     *
     * @return a list of all NewsPostModels in the cache
     */
    List<NewsPostModelImpl> getAll();

    /**
     * Puts a NewsPostModel into the cache with the specified key.
     *
     * @param key the key under which the model will be stored
     * @param value the NewsPostModel to store in the cache
     */
    void put(Long key, NewsPostModelImpl value);

    /**
     * Puts a list of NewsPostModels into the cache.
     *
     * @param allPosts a list of NewsPostModels to store in the cache
     */
    void putAll(List<NewsPostModelImpl> allPosts);

    /**
     * Removes a NewsPostModel from the cache based on the given key.
     *
     * @param key the key of the cache entry to remove
     */
    void evict(Long key);

    /**
     * Clears all entries from the cache.
     */
    void clear();

    /**
     * Returns the current number of entries in the cache.
     *
     * @return the size of the cache
     */
    int size();
}
