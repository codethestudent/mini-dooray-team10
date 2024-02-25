package com.nhnacademy.minidooray.gateway.exception;

/**
 * 탈퇴한 회원의 ID,PASSWORD 로 로그인 시도한 경우
 */
public class HandleUserWithdrawalException extends RuntimeException {
    public HandleUserWithdrawalException(String message) {
        super(message);
    }
}
