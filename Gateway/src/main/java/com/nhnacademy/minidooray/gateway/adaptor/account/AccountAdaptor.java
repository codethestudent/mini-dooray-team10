package com.nhnacademy.minidooray.gateway.adaptor.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;

public interface AccountAdaptor {

    AccountDto loginExistUser(LoginRequest loginRequest);

}
