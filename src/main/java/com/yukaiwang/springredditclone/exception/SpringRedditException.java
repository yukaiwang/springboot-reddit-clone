package com.yukaiwang.springredditclone.exception;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String message) {
        super(message);
    }

    public SpringRedditException(String message, Exception ex) {
        super(message, ex);
    }
}
