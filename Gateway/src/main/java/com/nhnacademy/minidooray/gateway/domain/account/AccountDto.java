package com.nhnacademy.minidooray.gateway.domain.account;

import lombok.*;

@Data
@Builder
public class AccountDto {

    private String userId;

    private String userEmail;

    private UserState userState;

}
