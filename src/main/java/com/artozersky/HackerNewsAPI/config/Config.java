package com.artozersky.HackerNewsAPI.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import com.artozersky.HackerNewsAPI.cache.CacheEntityManager;

/**
 * Configuration class for beans like CacheManager and ModelMapper.
 */
@Configuration
public class Config implements IConfig{

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
   
}
