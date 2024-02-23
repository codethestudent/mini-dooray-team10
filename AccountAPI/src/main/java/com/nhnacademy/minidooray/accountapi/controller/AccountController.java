package com.nhnacademy.minidooray.accountapi.controller;

import com.nhnacademy.minidooray.accountapi.domain.*;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    /**
     * userId를 가지고 Db에서 데이터를 반환한다. (단일)
     * GetMapping
     * @param userId
     * @return
     */
    @GetMapping("/get/user")
    public ResponseEntity<Account> getAccount(String userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            Account account = accountService.getAccount(userId);
            return ResponseEntity.ok(account);
        }
    }

    /**
     * userState가 ACTIVE인 사람만 List에 담아서 보내준다.
     *
     * @return accountList
     */
    @GetMapping("/get/users")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accountList = accountService.getAccounts()
                .stream()
                .filter(account -> UserState.ACTIVE.equals(account.getUserState()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(accountList);
    }

    @DeleteMapping("/{AccountId}")
    public ResultResponse deleteAccount (@PathVariable("AccountId") String accountId) {
        try {
            accountService.deleteAccount(accountId);
            return new ResultResponse("OK");
        } catch (IllegalArgumentException e) {
            return new ResultResponse("ERROR : " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AccountRequest> createAccount(@RequestBody CreateAccountRequest account) {
        log.info("accout context :{}", account);
        accountService.createAccount(account);
        String userId = account.getId();
        String userEmail = account.getEmail();

        AccountRequest accountRequest = new AccountRequest(userId, userEmail, UserState.ACTIVE);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody LoginRequest account) {
        log.info("account id is :{} ",account);
        boolean loggedIn = accountService.login(account.getId());

        if (loggedIn) {
            Account loginAccount = accountService.getAccount(account.getId());
            log.info("loginAccount send content is :{}", loginAccount.toString());
            return ResponseEntity.ok(loginAccount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/disabled/{id}")
    public ResponseEntity<AccountRequest> updateUserStateDisabled (@PathVariable("id") String accountId) {
        Account target = accountService.getAccount(accountId);
        AccountRequest targetAccount = new AccountRequest(target.getUserId(), target.getUserEmail(), UserState.DISABLED);
        return ResponseEntity.status(HttpStatus.OK).body(targetAccount);
    }

    @PutMapping("/update/active/{id}")
    public ResponseEntity<AccountRequest> updateUserStateActive (@PathVariable("id") String accountId) {
        Account target = accountService.getAccount(accountId);
        AccountRequest targetAccount = new AccountRequest(target.getUserId(), target.getUserEmail(), UserState.ACTIVE);
        return ResponseEntity.status(HttpStatus.OK).body(targetAccount);
    }
    //WITHDRAWAL
    @PutMapping("/udate/withdrawal/{id}")
    public ResponseEntity<AccountRequest> updateUserStateWithdrawal (@PathVariable("id") String accountId) {
        Account target = accountService.getAccount(accountId);
        AccountRequest targetAccount = new AccountRequest(target.getUserId(), target.getUserEmail(), UserState.WITHDRAWAL);
        return ResponseEntity.status(HttpStatus.OK).body(targetAccount);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<Map<String, Integer>> logout (HttpSession httpSession) {
//        httpSession.invalidate();
//        Map<String, Integer> response = new HashMap<>();
//        response.put("result_code", 0);
//
//        return ResponseEntity.ok(response);
//    }
}