package com.nhnacademy.minidooray.gateway.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/account")
public class LoginController {

    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String tryToLogin(@ModelAttribute LoginRequest loginRequest, HttpServletRequest httpServletRequest, Model model) throws Exception {

        //accountDto 받아와서
//        AccountDto accountDto = accountService.getAccount(loginRequest);
//
//        if (Objects.isNull(accountDto)) {
//            return "redirect:/login";
//        }
        AccountDto accountDto = AccountDto.builder().userId("id1").userEmail("email1").build();

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", accountDto.getUserId());
        session.setAttribute("account",accountDto);

        model.addAttribute("account", accountDto);
        return "dooray";
    }


}
