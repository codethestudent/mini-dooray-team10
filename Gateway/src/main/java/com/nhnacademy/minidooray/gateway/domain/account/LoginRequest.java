package com.nhnacademy.minidooray.gateway.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String id;
    private String password;

}
