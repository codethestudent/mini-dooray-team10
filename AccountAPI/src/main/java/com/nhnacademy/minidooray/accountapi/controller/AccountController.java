package com.nhnacademy.minidooray.accountapi.controller;

import com.nhnacademy.minidooray.accountapi.domain.Account;
import com.nhnacademy.minidooray.accountapi.domain.UserState;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        boolean loggedIn = accountService.login(account.getUserId(), account.getUserPassword());

        if (loggedIn) {
            Account loginAccount = accountService.getAccount(account.getUserId());
            return ResponseEntity.ok(loginAccount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Account> updateUserState (@RequestBody Account account, UserState userState) {
        Account target = accountService.getAccount(account.getUserId());
        accountService.updateUserState(target.getUserId(), userState);
        return ResponseEntity.status(HttpStatus.OK).body(target);
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