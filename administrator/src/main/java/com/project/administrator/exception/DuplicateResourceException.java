package com.project.administrator.exception;

public class DuplicateResourceException extends RuntimeException{
    private int statusCode;

    public DuplicateResourceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public DuplicateResourceException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
