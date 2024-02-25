//package com.nhnacademy.minidooray.gateway.advice;
//
//import com.nhnacademy.minidooray.gateway.exception.HandleAuthenticationFailureException;
//import com.nhnacademy.minidooray.gateway.exception.HandleDuplicateIdException;
//import com.nhnacademy.minidooray.gateway.exception.HandleUserIdNotFoundException;
//import com.nhnacademy.minidooray.gateway.exception.HandleUserWithdrawalException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Slf4j
//@ControllerAdvice
//public class ResponseEntityControllerAdvice extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler({HandleAuthenticationFailureException.class, HandleUserIdNotFoundException.class})
//    public ResponseEntity<Object> handleNotExistPassword(RuntimeException ex,WebRequest webRequest) {
//        log.info("ControllerAdvice - handleNotExistPassword");
//        String bodyOfResponse = "ID PW does not matches";
//        if (ex.equals(HandleAuthenticationFailureException.class)) {
//            return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, webRequest);
//        }
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
//    }
//
//    @ExceptionHandler(HandleDuplicateIdException.class)
//    public ResponseEntity<Object> handleDuplicatedId(HandleDuplicateIdException ex, WebRequest webRequest) {
//        log.info("ControllerAdvice - handleDuplicatedId");
//        String bodyOfReponse = "Duplicate Id - ResponseEntityControllerAdvice";
//
//        return handleExceptionInternal(ex, bodyOfReponse, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
//    }
//
//    @ExceptionHandler(HandleUserWithdrawalException.class)
//    public ResponseEntity<Object> handleTryToLoginWithdrawal(HandleUserWithdrawalException ex, WebRequest webRequest) {
//        log.info("ControllerAdvice - handleTryToLoginWithdrawal");
//        String bodyOfReponse = "Login Withdrawal - ResponseEntityControllerAdvice";
//
//        return handleExceptionInternal(ex, bodyOfReponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, webRequest);
//    }
//
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        if (body == null) {
//            body = "Something went wrong";
//        }
//        return new ResponseEntity<>(body, headers, status);
//    }
//
//}
