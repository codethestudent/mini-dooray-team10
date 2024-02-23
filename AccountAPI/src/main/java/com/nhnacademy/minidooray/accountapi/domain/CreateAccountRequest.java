package com.nhnacademy.minidooray.accountapi.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateAccountRequest {
    private String userId;
    private String userPassword;
    private String userEmail;
}
