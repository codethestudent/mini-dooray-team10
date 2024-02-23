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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get/user")
    public ResponseEntity<Account> getAccount(HttpSession httpSession) {
        String userId = (String) httpSession.getAttribute("user");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            Account account = accountService.getAccount(userId);
            return ResponseEntity.ok(account);
        }
    }

    @GetMapping("/get/users")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accountList = accountService.getAccounts();
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
        accountService.createAccount(account);

        String userId = account.getUserId();
        String userEmail = account.getUserEmail();

        AccountRequest accountRequest = new AccountRequest(userId, userEmail, UserState.ACTIVE);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody LoginRequest account) {
        log.info("account id is :{} ",account);
        boolean loggedIn = accountService.login(account.getId());

        if (loggedIn) {
            Account loginAccount = accountService.getAccount(account.getId());
            log.info("loginAccount send content is :{}", loginAccount);
            return ResponseEntity.ok(loginAccount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/update/disabled")
    public ResponseEntity<Account> updateUserStateDisabled (@RequestBody String accountId) {
        Account target = accountService.getAccount(accountId);
        if (target != null) {
            accountService.updateUserState(target.getUserId(), UserState.DISABLED);
            return ResponseEntity.status(HttpStatus.OK).body(target);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/update/active")
    public ResponseEntity<Account> updateUserStateActive (@RequestBody String accountId) {
        Account target = accountService.getAccount(accountId);
        if (target != null) {
            accountService.updateUserState(target.getUserId(), UserState.ACTIVE);
            return ResponseEntity.status(HttpStatus.OK).body(target);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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