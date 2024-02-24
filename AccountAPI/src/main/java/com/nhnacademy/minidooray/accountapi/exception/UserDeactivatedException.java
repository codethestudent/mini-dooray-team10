package com.nhnacademy.minidooray.accountapi.exception;

public class UserDeactivatedException extends RuntimeException{
    public UserDeactivatedException(String id) {
        super("회원 탈퇴한 계정입니다. 회원 가입 후 이용해 주시기 바랍니다." + id);
    }
}
