package com.artozersky.HackerNewsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.artozersky.HackerNewsAPI.repository")
@EntityScan(basePackages = "com.artozersky.HackerNewsAPI.model")
public class HackerNewsAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(HackerNewsAPIApplication.class, args);
    }
}
