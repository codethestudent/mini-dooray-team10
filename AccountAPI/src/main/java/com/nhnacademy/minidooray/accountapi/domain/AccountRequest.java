package com.nhnacademy.minidooray.accountapi.domain;

import lombok.Getter;

@Getter
public class AccountRequest {
    private String userId;

    private String userEmail;

    private UserState userState;
}
