package com.artozersky.HackerNewsAPI.exception;

public class CacheRetrievalException extends Exception{
    public CacheRetrievalException(String message) {
        super(message);
    }
    
    public CacheRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

}
