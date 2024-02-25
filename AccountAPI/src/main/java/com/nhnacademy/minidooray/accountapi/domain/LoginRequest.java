package com.nhnacademy.minidooray.accountapi.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {
    private String id;

    private String password;
}
