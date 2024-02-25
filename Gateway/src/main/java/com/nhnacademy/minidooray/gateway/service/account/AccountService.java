package com.nhnacademy.minidooray.gateway.service.account;

import com.nhnacademy.minidooray.gateway.domain.account.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.account.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.account.SignupRequest;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAccountList();

    AccountDto getAccount(LoginRequest loginRequest);

    AccountDto createAccount(SignupRequest signupRequest);

    AccountDto updateDisable(String id);

    AccountDto updateActive(String id);

    AccountDto deleteUser(String id);
}
