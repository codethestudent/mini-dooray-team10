package com.nhnacademy.minidooray.gateway.adaptor.account;

import com.nhnacademy.minidooray.gateway.config.AccountAdatptorProperties;
import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;
import com.nhnacademy.minidooray.gateway.domain.SignupRequest;
import com.nhnacademy.minidooray.gateway.exception.HandleAuthenticationFailureException;
import com.nhnacademy.minidooray.gateway.exception.HandleDuplicateIdException;
import com.nhnacademy.minidooray.gateway.exception.HandleUserIdNotFoundException;
import com.nhnacademy.minidooray.gateway.exception.HandleUserWithdrawalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class AccountAdaptorImpl implements AccountAdaptor{

    private final RestTemplate restTemplate;

    private final AccountAdatptorProperties accountAdatptorProperties;

    public AccountAdaptorImpl(RestTemplate restTemplate, AccountAdatptorProperties accountAdatptorProperties) {
        this.restTemplate = restTemplate;
        this.accountAdatptorProperties = accountAdatptorProperties;
    }

    /**
     * @exception HandleAuthenticationFailureException : 없는 PW 로그인 요청
     * @exception HandleUserIdNotFoundException : 없는 ID로 로그인 요청
     * @exception HandleUserWithdrawalException : 탈퇴한 회원의 ID PW로 로그인 요청
     * @param loginRequest : 사용자의 요청으로 받은 ID,PW로 매핑된 Dto 객체
     * @return AccountDto : PW를 제외한 Account 객체
     */
    @Override
    public AccountDto loginExistUser(LoginRequest loginRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<LoginRequest> requestHttpEntity = new HttpEntity<>(loginRequest,httpHeaders);
        ResponseEntity<AccountDto> respEntity = restTemplate.exchange(
                accountAdatptorProperties.getUrlName() + "/user/login",
                HttpMethod.POST,
                requestHttpEntity,
                AccountDto.class);

        if(respEntity.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            throw new HandleAuthenticationFailureException("Invalid id or password");
        } else if (respEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new HandleUserIdNotFoundException("Account id is not found");
        } else if (respEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            throw new HandleUserWithdrawalException("This account has been withdrawal");
        }
        return respEntity.getBody();
    }

    /**
     * @exception HandleDuplicateIdException : 중복된 ID로 회원가입 요청
     * @param signupRequest : 회원가입 정보로 매핑된 DTO 객체
     * @return AccountDto : 회원가입에 성공한 회원의 DTO 객체
     */
    @Override
    public AccountDto createUser(SignupRequest signupRequest) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            try {
                HttpEntity<SignupRequest> requestHttpEntity = new HttpEntity<>(signupRequest, httpHeaders);
                ResponseEntity<AccountDto> respEntity = restTemplate.exchange(
                        accountAdatptorProperties.getUrlName() + "/user/create",
                        HttpMethod.POST,
                        requestHttpEntity,
                        AccountDto.class
                );

                return respEntity.getBody();
            } catch (Exception exception) {
                throw new HandleDuplicateIdException("Account is already exist - gateway");
            }
    }

    @Override
    public AccountDto updateStateToDisable(String id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        log.info("{}", accountAdatptorProperties.getUrlName() + "/update/dsiabled/"+id);

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(id, httpHeaders);
        ResponseEntity<AccountDto> respEntity = restTemplate.exchange(
                accountAdatptorProperties.getUrlName() + "/user/update/disabled/" + id,
                HttpMethod.PUT,
                requestHttpEntity,
                AccountDto.class,
                id
        );
        if(!respEntity.getStatusCode().equals(HttpStatus.OK)) throw new RuntimeException();
        return respEntity.getBody();
    }

    @Override
    public AccountDto updateStateToActive(String id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(id, httpHeaders);
        ResponseEntity<AccountDto> respEntity = restTemplate.exchange(
                accountAdatptorProperties.getUrlName() + "/user/update/active/" + id,
                HttpMethod.PUT,
                requestHttpEntity,
                AccountDto.class,
                id
        );
        if(!respEntity.getStatusCode().equals(HttpStatus.OK)) throw new RuntimeException();
        return respEntity.getBody();
    }

    @Override
    public AccountDto deleteUser(String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(id, httpHeaders);
        ResponseEntity<AccountDto> respEntity = restTemplate.exchange(
                accountAdatptorProperties.getUrlName() + "/user/update/withdrawal/" + id,
                HttpMethod.PUT,
                requestHttpEntity,
                AccountDto.class,
                id
        );
        if(!respEntity.getStatusCode().equals(HttpStatus.OK)) throw new RuntimeException();
        return respEntity.getBody();
    }


}
