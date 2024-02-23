package com.nhnacademy.minidooray.gateway.service.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.SignupRequest;

public interface AccountService {

    AccountDto getAccount(LoginRequest loginRequest);

    AccountDto createAccount(SignupRequest signupRequest);

}
