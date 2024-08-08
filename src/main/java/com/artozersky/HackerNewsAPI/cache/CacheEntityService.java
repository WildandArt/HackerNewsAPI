package com.artozersky.HackerNewsAPI.cache;

import java.util.List;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

/**
 * Interface for cache operations related to {@link NewsPostModelImpl} entities.
 * Provides methods for retrieving, storing, and managing cached posts.
 */
public interface CacheEntityService {

    /**
     * Retrieves a post from the cache by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The cached {@link NewsPostModelImpl} if found, or null if not present in the cache.
     */
    NewsPostModelImpl getPostFromCacheById(Long postId);

    /**
     * Retrieves all posts currently stored in the cache.
     *
     * @return A list of all cached {@link NewsPostModelImpl} entities.
     */
    List<NewsPostModelImpl> getAllPostsFromCache();

    /**
     * Stores a single post in the cache.
     *
     * @param post The {@link NewsPostModelImpl} to store in the cache.
     */
    void putPostInCache(NewsPostModelImpl post);

    /**
     * Stores a list of posts in the cache.
     *
     * @param posts The list of {@link NewsPostModelImpl} entities to store in the cache.
     */
    void putAllPostsInCache(List<NewsPostModelImpl> posts);

    /**
     * Removes a post from the cache by its ID.
     *
     * @param postId The ID of the post to evict from the cache.
     */
    void evictPost(Long postId);

    /**
     * Clears all entries from the cache.
     */
    void clearCache();

    /**
     * Checks if the cached posts are stale by comparing their elapsed time.
     *
     * @param cachedPosts The list of cached {@link NewsPostModelImpl} entities to check.
     * @return True if the cached posts are stale, false otherwise.
     */
    boolean checkIfStale(List<NewsPostModelImpl> cachedPosts);
}
