package com.nhnacademy.minidooray.gateway.service.account;

import com.nhnacademy.minidooray.gateway.adaptor.account.AccountAdaptor;
import com.nhnacademy.minidooray.gateway.domain.account.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.account.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.account.SignupRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountAdaptor accountAdaptor;

    public AccountServiceImpl(AccountAdaptor accountAdaptor) {
        this.accountAdaptor = accountAdaptor;
    }

    @Override
    public List<AccountDto> getAccountList() {
        return accountAdaptor.getUserList();
    }

    @Override
    public AccountDto getAccount(LoginRequest loginRequest) {
        return accountAdaptor.loginExistUser(loginRequest);
    }

    @Override
    public AccountDto createAccount(SignupRequest signupRequest) {
        return accountAdaptor.createUser(signupRequest);
    }

    @Override
    public AccountDto updateDisable(String id) {
        return accountAdaptor.updateStateToDisable(id);
    }

    public AccountDto updateActive(String id) {
        return accountAdaptor.updateStateToActive(id);
    }

    @Override
    public AccountDto deleteUser(String id) {
        return accountAdaptor.deleteUser(id);
    }


}
