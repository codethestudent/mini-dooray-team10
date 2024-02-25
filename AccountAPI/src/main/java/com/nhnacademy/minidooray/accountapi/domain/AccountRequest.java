package com.nhnacademy.minidooray.accountapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AccountRequest {
    private String userId;

    private String userEmail;

    private UserState userState;
}
