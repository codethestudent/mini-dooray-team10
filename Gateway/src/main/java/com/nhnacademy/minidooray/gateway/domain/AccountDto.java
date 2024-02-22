package com.nhnacademy.minidooray.gateway.domain;

import lombok.*;

@Data
@Builder
public class AccountDto {

    private String userId;

    private String userEmail;

    private UserState userState;

}
