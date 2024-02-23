package com.nhnacademy.minidooray.gateway.controller.account;

import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.SignupRequest;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/registry")
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/signup")
    public String getLoginForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String tryToSignup(@ModelAttribute SignupRequest signupRequest, RedirectAttributes redirectAttributes){

        log.info("회원가입 ID : {}", signupRequest);

        AccountDto accountDto = accountService.createAccount(signupRequest);

        if (Objects.nonNull(accountDto)) {
            redirectAttributes.addFlashAttribute("message", "회원가입 성공 !!");
        } else {
            redirectAttributes.addFlashAttribute("message","회원가입 실패 !!");
        }

        return "redirect:/";
    }

    @GetMapping("/withdrawal/{id}")
    public String tryToWithdrawal(@PathVariable("id") String id, HttpSession session, RedirectAttributes redirectAttributes) {

        AccountDto accountDto = accountService.deleteUser(id);

        if(Objects.nonNull(accountDto)) {
            redirectAttributes.addFlashAttribute("message", "회원 삭제 성공 !!");
            session.invalidate();
        } else {
            redirectAttributes.addFlashAttribute("message", "회원 삭제 실패 !!");
        }

        return "redirect:/";
    }

}
