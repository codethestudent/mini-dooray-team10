package com.nhnacademy.minidooray.accountapi.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String id) {
        super("ID 혹은 password가 일치하지 않습니다. 다시 확인해 주시기 바랍니다." + id);
    }
}
