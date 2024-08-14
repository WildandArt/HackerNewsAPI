package com.artozersky.HackerNewsAPI.cache;

import java.util.List;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

/**
 * @brief Interface for cache operations related to {@link NewsPostModelImpl} entities.
 * 
 * This interface provides methods for retrieving, storing, and managing cached posts, 
 * ensuring efficient access and manipulation of cached data.
 */
public interface CacheEntityService {

    /**
     * @brief Retrieves a post from the cache by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The cached {@link NewsPostModelImpl} if found, or null if not present in the cache.
     */
    NewsPostModelImpl getPostById(Long postId);

    /**
     * @brief Retrieves all posts currently stored in the cache.
     *
     * @return A list of all cached {@link NewsPostModelImpl} entities.
     */
    List<NewsPostModelImpl> getAllPosts();

    /**
     * @brief Stores a single post in the cache.
     *
     * @param post The {@link NewsPostModelImpl} to store in the cache.
     */
    void putPost(NewsPostModelImpl post);

    /**
     * @brief Stores a list of posts in the cache.
     *
     * @param posts The list of {@link NewsPostModelImpl} entities to store in the cache.
     */
    void putAllPosts(List<NewsPostModelImpl> posts);

    /**
     * @brief Removes a post from the cache by its ID.
     *
     * @param postId The ID of the post to evict from the cache.
     */
    void evictPost(Long postId);

    /**
     * @brief Clears all entries from the cache.
     */
    void clearCache();

    /**
     * @brief Retrieves the post with the lowest score from the cache.
     *
     * @return The {@link NewsPostModelImpl} with the lowest score.
     */
    public NewsPostModelImpl getLowestScorePost();

    /**
     * @brief Gets the current size of the cache.
     *
     * @return The current number of posts in the cache.
     */
    public Integer getSize();

    /**
     * @brief Gets the maximum size limit of the cache.
     *
     * @return The maximum number of posts that the cache can hold.
     */
    public Integer getMaxSize();
}
