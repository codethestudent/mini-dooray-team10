package com.nhnacademy.minidooray.gateway.exception;

/**
 * ID, PASSWORD 가 틀린 경우의 예외
 */
public class HandleAuthenticationFailureException extends RuntimeException {
    public HandleAuthenticationFailureException(String message) {
        super(message);
    }
}
