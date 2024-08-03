package com.artozersky.HackerNewsAPI.config;

import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;

/**
 * Interface for application configuration.
 */
public interface IConfig {
    /**
     * Returns a CacheManager bean with a configurable cache size.
     * @return the CacheManager bean
     */
    CacheManager cacheManager();

    /**
     * Returns a ModelMapper bean for object mapping.
     * @return the ModelMapper bean
     */
    ModelMapper modelMapper();
}
