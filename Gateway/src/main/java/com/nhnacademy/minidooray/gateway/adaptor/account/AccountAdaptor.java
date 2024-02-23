package com.nhnacademy.minidooray.gateway.adaptor.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.SignupRequest;

public interface AccountAdaptor {

    AccountDto loginExistUser(LoginRequest loginRequest);

    AccountDto createUser(SignupRequest signupRequest);

}
