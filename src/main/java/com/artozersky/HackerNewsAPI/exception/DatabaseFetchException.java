package com.artozersky.HackerNewsAPI.exception;


public class DatabaseFetchException extends Exception {

    public DatabaseFetchException(String message) {
        super(message);
    }

    public DatabaseFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
