package com.artozersky.HackerNewsAPI.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artozersky.HackerNewsAPI.cache.CacheEntityManager;

/**
 * Configuration class for beans like CacheManager and ModelMapper.
 */
@Configuration
public class Config implements IConfig{

    @Value("${cache.size:5}")
    private int cacheSize;

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        return new CacheEntityManager(cacheSize);
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
   
}
