package com.artozersky.HackerNewsAPI.cache;


import java.util.List;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;


public interface CacheEntityService {

    NewsPostModelImpl getPostFromCacheById(Long postId);

    List<NewsPostModelImpl> getAllPostsFromCache();

    void putPostInCache(NewsPostModelImpl post);

    void putAllPostsInCache(List<NewsPostModelImpl> posts);

    void evictPost(Long postId);

    void clearCache();

    boolean checkIfStale(List<NewsPostModelImpl> cachedPosts);
}
