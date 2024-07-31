package com.artozersky.HackerNewsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

// check if you can remove this if yes do it.
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.artozersky.HackerNewsAPI.repository") // try to fix this, we don't want this here.
@EntityScan(basePackages = "com.artozersky.HackerNewsAPI.model") // try to fix this, we don't want this here.
@EnableCaching
public class HackerNewsAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(HackerNewsAPIApplication.class, args);
    }
}
