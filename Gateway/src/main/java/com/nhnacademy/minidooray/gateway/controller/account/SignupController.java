package com.nhnacademy.minidooray.gateway.controller.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.SignupRequest;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Slf4j
@Controller
public class SignupController {

    private final AccountService accountService;

    public SignupController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public String tryToSignup(@ModelAttribute SignupRequest signupRequest, RedirectAttributes redirectAttributes){

        log.info("회원가입 ID : {}", signupRequest);

        AccountDto accountDto = accountService.createAccount(signupRequest);

        log.info("회원가입 api 호출 후 받은 데이터 : {}", accountDto);

        if (Objects.nonNull(accountDto)) {
            redirectAttributes.addFlashAttribute("message", "회원가입 성공 !!");
        } else {
            redirectAttributes.addFlashAttribute("message","회원가입 실패 !!");
        }

        return "redirect:/";
    }

}
