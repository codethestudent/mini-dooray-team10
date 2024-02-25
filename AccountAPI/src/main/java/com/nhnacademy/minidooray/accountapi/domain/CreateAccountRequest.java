package com.nhnacademy.minidooray.accountapi.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateAccountRequest {
    private String id;
    private String password;
    private String email;
}
