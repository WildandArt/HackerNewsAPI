package com.artozersky.HackerNewsAPI.config;

import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artozersky.HackerNewsAPI.cache.CacheEntityManager;
// create interface and document with doxygen
@Configuration
public class Config{
    @Bean
    public CacheManager cacheManager(){     // @Value(
        return new CacheEntityManager(100); // 100? please let the user decide how many he can set do it inside the application properties and get the value from there. 
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}