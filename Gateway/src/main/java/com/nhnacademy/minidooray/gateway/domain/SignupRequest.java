package com.nhnacademy.minidooray.gateway.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// todo ToString 지우기
@Getter
@Setter
@ToString
public class SignupRequest {

    private String id;
    private String password;
    private String email;

}
