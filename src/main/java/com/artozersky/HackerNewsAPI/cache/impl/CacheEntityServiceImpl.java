package com.artozersky.HackerNewsAPI.cache.impl;


import com.artozersky.HackerNewsAPI.cache.CacheEntityService;


import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheEntityServiceImpl implements CacheEntityService {

    private final CacheEntityImpl cacheEntity;

    @Autowired
    public CacheEntityServiceImpl(@Value("${cache.size:100}") int maxSize) {
        this.cacheEntity = new CacheEntityImpl(maxSize);
    }

    @Override
    public NewsPostModelImpl getPostFromCacheById(Long postId) {

        return cacheEntity.get(postId);
    }

    @Override
    public List<NewsPostModelImpl> getAllPostsFromCache() {
        return cacheEntity.getAll();
    }

    @Override
    public void putPostInCache(NewsPostModelImpl post) {
        cacheEntity.put(post.getPostId(), post);
    }

    @Override
    public void putAllPostsInCache(List<NewsPostModelImpl> posts) {
        cacheEntity.putAll(posts);
    }

    @Override
    public void evictPost(Long postId) {
        cacheEntity.evict(postId);
    }

    @Override
    public void clearCache() {
        cacheEntity.clear();
    }

}
