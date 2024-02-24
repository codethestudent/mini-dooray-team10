package com.nhnacademy.minidooray.accountapi.exception;

public class AccountIdNotFoundExcption extends RuntimeException{
    public AccountIdNotFoundExcption(String id) {
        super(id + "는 존재하지 않는 ID입니다.");
    }
}
