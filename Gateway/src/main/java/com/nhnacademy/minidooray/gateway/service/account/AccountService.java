package com.nhnacademy.minidooray.gateway.service.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;

public interface AccountService {

    AccountDto getAccount(LoginRequest loginRequest);

}
