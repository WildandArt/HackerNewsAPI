package com.artozersky.HackerNewsAPI.cache;

import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

import java.util.List;

/**
 * @brief Interface for the cache entity that provides methods to manage cached posts.
 */
public interface CacheEntity {

    /**
     * @brief Retrieves a post from the cache by its key.
     * @param key The key of the post to retrieve.
     * @return The cached post, or null if not found.
     */
    NewsPostModelImpl get(Long key);

    /**
     * @brief Retrieves all posts from the cache.
     * @return A list of all cached posts, sorted by score in descending order.
     */
    List<NewsPostModelImpl> getAllPosts();

    /**
     * @brief Retrieves the post with the lowest score from the cache.
     * @return The post with the lowest score.
     */
    NewsPostModelImpl getLowestScorePost();

    /**
     * @brief Retrieves the maximum size of the cache.
     * @return The maximum number of entries the cache can hold.
     */
    Integer getMaxSize();

    /**
     * @brief Adds a post to the cache.
     * @param key The key of the post.
     * @param value The post to add.
     */
    void put(Long key, NewsPostModelImpl value);

    /**
     * @brief Adds a list of posts to the cache.
     * @param allPosts The list of posts to add.
     */
    void putAllPosts(List<NewsPostModelImpl> allPosts);

    /**
     * @brief Removes a post from the cache by its key.
     * @param key The key of the post to remove.
     */
    void evict(Long key);

    /**
     * @brief Clears all entries from the cache.
     */
    void clear();

    /**
     * @brief Retrieves the current number of entries in the cache.
     * @return The current size of the cache.
     */
    Integer size();
}
