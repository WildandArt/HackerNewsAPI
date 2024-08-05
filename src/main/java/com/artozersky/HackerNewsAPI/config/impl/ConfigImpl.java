package com.artozersky.HackerNewsAPI.config.impl;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artozersky.HackerNewsAPI.config.Config;

/**
 * Configuration class for beans like CacheManager and ModelMapper.
 */
@Configuration
public class ConfigImpl implements Config{

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
   
}
