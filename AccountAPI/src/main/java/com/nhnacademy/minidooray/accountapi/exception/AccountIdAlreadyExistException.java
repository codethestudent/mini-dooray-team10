package com.nhnacademy.minidooray.accountapi.exception;

public class AccountIdAlreadyExistException extends RuntimeException{
    public AccountIdAlreadyExistException(String id) {
        super(id + "는 이미 존재하는 ID 입니다.");
    }
}
