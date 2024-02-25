package com.nhnacademy.minidooray.accountapi.service.impl;

import com.nhnacademy.minidooray.accountapi.domain.Account;
import com.nhnacademy.minidooray.accountapi.domain.CreateAccountRequest;
import com.nhnacademy.minidooray.accountapi.domain.UserState;
import com.nhnacademy.minidooray.accountapi.exception.AccountIdAlreadyExistException;
import com.nhnacademy.minidooray.accountapi.exception.AccountIdNotFoundExcption;
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
            throw new AccountIdAlreadyExistException(account.getId());
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
            throw new AccountIdNotFoundExcption(accountId);
        }
    }

    @Override
    public boolean login(String accountId, String accountPassword) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null && account.getUserPassword().equals(accountPassword)) {
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
            throw new AccountIdNotFoundExcption(accountId);
        }
    }
}
