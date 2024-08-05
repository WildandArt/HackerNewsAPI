package com.artozersky.HackerNewsAPI.config;

import org.modelmapper.ModelMapper;

// import com.artozersky.HackerNewsAPI.cache.CacheEntityManager;

/**
 * Interface for application configuration.
 */
public interface IConfig {
    /**
     * Returns a ModelMapper bean for object mapping.
     * @return the ModelMapper bean
     */
    ModelMapper modelMapper();
}
