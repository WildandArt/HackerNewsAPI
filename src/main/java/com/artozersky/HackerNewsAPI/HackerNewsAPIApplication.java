package com.artozersky.HackerNewsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HackerNewsAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(HackerNewsAPIApplication.class, args);
    }
}
