package com.nhnacademy.minidooray.gateway.exception;

/**
 * ID, PASSWORD 로 조회했을 때 없는 회원일 경우
 */
public class HandleUserIdNotFoundException extends RuntimeException{
    public HandleUserIdNotFoundException(String message) {
        super(message);
    }
}
