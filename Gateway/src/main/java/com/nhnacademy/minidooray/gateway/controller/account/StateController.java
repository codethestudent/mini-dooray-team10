package com.nhnacademy.minidooray.gateway.controller.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/state")
public class StateController {

    private final AccountService accountService;

    public StateController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/disable/{id}")
    public String tryToDisable(@PathVariable String id, HttpSession session) {

        AccountDto disabledAccount = accountService.updateDisable(id);
        session.setAttribute("account", disabledAccount);

        return "redirect:/";
    }

    @GetMapping("/active/{id}")
    public String tryToActive(@PathVariable String id, HttpSession session) {

        AccountDto activeAccount = accountService.updateActive(id);
        session.setAttribute("account", activeAccount);

        return "redirect:/";
    }

}
