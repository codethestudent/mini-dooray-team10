package com.nhnacademy.minidooray.gateway.adaptor.account;

import com.nhnacademy.minidooray.gateway.domain.account.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.account.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.account.SignupRequest;

import java.util.List;

public interface AccountAdaptor {

    List<AccountDto> getUserList();

    AccountDto loginExistUser(LoginRequest loginRequest);

    AccountDto createUser(SignupRequest signupRequest);

    AccountDto updateStateToDisable(String id);

    AccountDto updateStateToActive(String id);

    AccountDto deleteUser(String id);

}
