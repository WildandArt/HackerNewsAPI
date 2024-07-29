package com.artozersky.HackerNewsAPI.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artozersky.HackerNewsAPI.cache.CacheEntityManager;

@Configuration
public class CacheConfig{
    @Bean
    public CacheManager cacheManager(){
        return new CacheEntityManager(100);
    }
}