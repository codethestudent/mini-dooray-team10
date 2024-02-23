package com.nhnacademy.minidooray.accountapi.service.impl;

import com.nhnacademy.minidooray.accountapi.domain.Account;
import com.nhnacademy.minidooray.accountapi.domain.CreateAccountRequest;
import com.nhnacademy.minidooray.accountapi.domain.UserState;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public Account createAccount( CreateAccountRequest account) {
        log.info("Create account address is :{}" , account);
        if (accountRepository.existsById(account.getId())) {
            throw new IllegalArgumentException("id : " + account.getId() + "는 이미 존재합니다.");
        }

        String newUserId = account.getId();
        String newUserPassword = account.getPassword();
        String newUserEmail = account.getEmail();

        Account newUser = new Account(newUserId, newUserPassword, newUserEmail, UserState.ACTIVE);

        return accountRepository.save(newUser);
    }

    @Override
    public void deleteAccount(String accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        } else {
            throw new IllegalArgumentException("id :" + accountId + "를 찾을 수 없습니다. 삭제에 실패하였습니다.");
        }
    }

    @Override
    public boolean login(String accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            return true;
        }
        return false;
    }

    @Override
    public void updateUserState(String accountId, UserState userState) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            account.setUserState(userState);
            accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("user id : " + accountId + "는 존재하지 않는 id 입니다. 상태 변경에 실패하였습니다.");
        }
    }

//    @Override
//    public void logout(String accountId) {
//        Account account = accountRepository.findById(accountId).orElse(null);
//        if (account != null) {
//
//        }
//    }
}
