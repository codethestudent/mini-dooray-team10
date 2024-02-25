package com.nhnacademy.minidooray.gateway.controller.account;

import com.nhnacademy.minidooray.gateway.domain.account.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.account.LoginRequest;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String getLogin(Model model) {
        log.info("redirect message:{}", model.getAttribute("message"));
        return "login";
    }


    @PostMapping("/login")
    public String tryToLogin(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {

        log.info("loginRequest id : {} / pw : {}", loginRequest.getId(), loginRequest.getPassword());

        AccountDto accountDto = accountService.getAccount(loginRequest);

        log.info("accountDto from Account-api : {}",accountDto);

        if (Objects.isNull(accountDto)) {
            log.info("redirectAttributes Test");
            redirectAttributes.addFlashAttribute("message", "로그인 실패");
            return "redirect:/account/login";
        }

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", accountDto.getUserId());
        session.setAttribute("account",accountDto);
        session.setMaxInactiveInterval(2*60*60);

        redirectAttributes.addFlashAttribute("id", accountDto.getUserId());
        return "redirect:/task/dooray";
    }

    @GetMapping("/logout")
    public String tryToLogout(HttpServletRequest request, @CookieValue("JSESSIONID") Cookie cookie) {

        HttpSession session = request.getSession(false);

        cookie.setMaxAge(0);
        session.invalidate();
        session.setMaxInactiveInterval(0);

        return "redirect:/";
    }

}
