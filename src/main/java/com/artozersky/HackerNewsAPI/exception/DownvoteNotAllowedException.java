package com.artozersky.HackerNewsAPI.exception;

public class DownvoteNotAllowedException extends RuntimeException {

    public DownvoteNotAllowedException(String message) {
        super(message);
    }
}
