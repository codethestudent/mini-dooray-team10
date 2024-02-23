package com.nhnacademy.minidooray.gateway.service.account;

import com.nhnacademy.minidooray.gateway.adaptor.account.AccountAdaptor;
import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountAdaptor accountAdaptor;

    public AccountServiceImpl(AccountAdaptor accountAdaptor) {
        this.accountAdaptor = accountAdaptor;
    }

    @Override
    public AccountDto getAccount(LoginRequest loginRequest) {
        return accountAdaptor.loginExistUser(loginRequest);
    }

    @Override
    public AccountDto createAccount(SignupRequest signupRequest) {
        return accountAdaptor.createUser(signupRequest);
    }


}
