package com.nhnacademy.minidooray.accountapi.service.impl;

import com.nhnacademy.minidooray.accountapi.domain.Account;
import com.nhnacademy.minidooray.accountapi.domain.UserState;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public Account createAccount(Account account) {
        if (accountRepository.existsById(account.getUserId())) {
            throw new IllegalArgumentException("id : " + account.getUserId() + "는 이미 존재합니다.");
        }
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        } else {
            throw new IllegalArgumentException("id :" + accountId + "를 찾을 수 없습니다. 삭제에 실패하였습니다.");
        }
    }

    /**
     *  userState : LOGIN, LOGOUT, DORMANT
     * @param accountId : 계정 id.
     * @param userState : 조정할 유저 상태.
     */
    @Override
    public void dormantAccount(String accountId, UserState userState) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            account.setUserState(userState);
            accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("id : " + accountId +" 를 찾을 수 없습니다. 계정 정보의 수정이 실패했습니다.");
        }
    }

    @Override
    public boolean login(String accountId, String accountPassword) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null && account.getUserPassword().equals(accountPassword) && account.getUserState().equals(UserState.DORMANT)) {
            dormantAccount(accountId, UserState.LOGIN);
            return true;
        }
        return false;
    }

    @Override
    public void logout(String accountId) {
        dormantAccount(accountId, UserState.LOGOUT);
    }
}
