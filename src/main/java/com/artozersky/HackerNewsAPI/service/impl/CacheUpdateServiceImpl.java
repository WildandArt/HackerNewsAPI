package com.artozersky.HackerNewsAPI.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityServiceImpl;
import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.CacheUpdateService;

import jakarta.annotation.PostConstruct;


@Service
public class CacheUpdateServiceImpl implements CacheUpdateService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CacheEntityServiceImpl cacheService;

    private final Integer cacheSize;

    public CacheUpdateServiceImpl(@Value("${cache.size:100}") Integer cacheSize) {
        this.cacheSize = cacheSize;
    }

    @PostConstruct
    public void init() {
        updateTimeElapsedAndRefreshCache();
    }

    @Override
    @Async
    @Scheduled(fixedRateString = "${db.update.interval:3600000}")  // default 3600000 milliseconds = 1 hour
    public void updateTimeElapsedAndRefreshCache() {
        // Fetch all posts from the database
        List<NewsPostModelImpl> allPosts = postRepository.findAll();
        

        // Update the timeElapsed field for each post
        for (NewsPostModelImpl post : allPosts) {

            Integer newTimeElapsed = (int) java.time.Duration.between(post.getCreatedAt(), LocalDateTime.now()).toHours();
            post.setTimeElapsed(newTimeElapsed);
            postRepository.save(post);

        }

        // Clear and refresh the cache
        cacheService.clearCache();

        List<NewsPostModelImpl> topPosts = postRepository.findTopPostsByScore(PageRequest.of(0, cacheSize));

        cacheService.putAllPosts(topPosts);

    }
}
