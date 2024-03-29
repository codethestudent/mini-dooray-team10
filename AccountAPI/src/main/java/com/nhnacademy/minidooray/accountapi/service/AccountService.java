package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.domain.Account;
import com.nhnacademy.minidooray.accountapi.domain.CreateAccountRequest;
import com.nhnacademy.minidooray.accountapi.domain.UserState;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();

    Account getAccount(String accountId);

    Account createAccount(CreateAccountRequest account);

    void deleteAccount (String accountId);

//    void dormantAccount (String accountId, UserState userState);

    boolean login (String accountId, String accountPassword);

//    void logout (String accountId);
    void updateUserState (String accountId, UserState userState);
}
