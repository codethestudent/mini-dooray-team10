package com.nhnacademy.minidooray.gateway.advice;

import com.nhnacademy.minidooray.gateway.exception.HandleAuthenticationFailureException;
import com.nhnacademy.minidooray.gateway.exception.HandleDuplicateIdException;
import com.nhnacademy.minidooray.gateway.exception.HandleUserIdNotFoundException;
import com.nhnacademy.minidooray.gateway.exception.HandleUserWithdrawalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({HttpClientErrorException.NotFound.class, HttpClientErrorException.Forbidden.class})
    public String handleNotExistPassword(RedirectAttributes redirectAttributes ) {
        log.info("ControllerAdvice - notExistPassword ");
        redirectAttributes.addFlashAttribute("message", "잘못된 아이디 또는 패스워드 입력");

        return "redirect:/account/login";
    }

    @ExceptionHandler(HandleDuplicateIdException.class)
    public String handleDuplicatedId(RedirectAttributes redirectAttributes) {
        log.info("ControllerAdvice - duplicatedId");
        redirectAttributes.addFlashAttribute("message", "중복된 아이디 입력");
        return "redirect:/account/login";
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public String handleTryToLoginWithWithdrawal(RedirectAttributes redirectAttributes) {
        log.info("ControllerAdvice - duplicatedId");
        redirectAttributes.addFlashAttribute("message", "회원탈퇴한 계정");

        return "redirect:/account/login";
    }

}
