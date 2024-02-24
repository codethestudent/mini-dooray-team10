package com.nhnacademy.minidooray.accountapi.advice;

import com.nhnacademy.minidooray.accountapi.exception.AccountIdAlreadyExistException;
import com.nhnacademy.minidooray.accountapi.exception.AccountIdNotFoundExcption;
import com.nhnacademy.minidooray.accountapi.exception.InvalidCredentialsException;
import com.nhnacademy.minidooray.accountapi.exception.UserDeactivatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 예외 처리 4종. handling
 * <p>
 * ID가 이미 존재할 때, accountIdAlreadyExist / CONFLICT
 * <p>
 * ID를 찾을 수 없을 때, accountIdNotFound / NOT_FOUND
 * <p>
 * ID 혹은 Password가 일치하지 않을 때, invalidCredential / FORBIDDEN
 * <p>
 * 회원 탈퇴한 계정일 때. userDeactivate / UNAUTHORIZED
 */
@Slf4j
@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(AccountIdAlreadyExistException.class)
    public ResponseEntity<String> accountIdAlreadyExist(AccountIdAlreadyExistException accountIdAlreadyExistException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Account is already exist");
    }

    @ExceptionHandler(AccountIdNotFoundExcption.class)
    public ResponseEntity<String> accountIdNotFound ( AccountIdNotFoundExcption accountIdNotFoundExcption) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account id is not found");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> invalidCredential (InvalidCredentialsException invalidCredentialsException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid id or password");
    }

    @ExceptionHandler (UserDeactivatedException.class)
    public ResponseEntity<String> userDeactivate (UserDeactivatedException userDeactivatedException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This account has been deactivated");
    }

}
