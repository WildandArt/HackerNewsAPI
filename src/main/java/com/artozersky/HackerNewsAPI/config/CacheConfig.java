package com.artozersky.HackerNewsAPI.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig{
    @Bean
    public CacheManager cacheManager(){
        return new CustomCacheManager(100);
    }
}