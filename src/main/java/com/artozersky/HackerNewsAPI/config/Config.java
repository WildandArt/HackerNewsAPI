package com.artozersky.HackerNewsAPI.config;

import org.modelmapper.ModelMapper;

/**
 * Interface for application configuration.
 */
public interface Config {
    /**
     * Returns a ModelMapper bean for object mapping.
     * @return the ModelMapper bean
     */
    ModelMapper modelMapper();
}
