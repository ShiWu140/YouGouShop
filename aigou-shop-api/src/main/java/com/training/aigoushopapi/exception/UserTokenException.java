package com.training.aigoushopapi.exception;

/**
 * Token异常
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/19 10:16
 */
public class UserTokenException extends RuntimeException {
    public UserTokenException(String message) {
        super(message);
    }
}
