package com.artozersky.HackerNewsAPI.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public interface CacheUpdateService {

    @Async
    @Scheduled(fixedRateString = "${db.update.interval:3600000}")
    void updateTimeElapsedAndRefreshCache();
}
