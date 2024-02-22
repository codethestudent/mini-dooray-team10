package com.nhnacademy.minidooray.gateway.adaptor.account;

import com.nhnacademy.minidooray.gateway.config.AccountAdatptorProperties;
import com.nhnacademy.minidooray.gateway.domain.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.LoginRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AccountAdaptorImpl implements AccountAdaptor{

    private final RestTemplate restTemplate;

    private final AccountAdatptorProperties accountAdatptorProperties;

    public AccountAdaptorImpl(RestTemplate restTemplate, AccountAdatptorProperties accountAdatptorProperties) {
        this.restTemplate = restTemplate;
        this.accountAdatptorProperties = accountAdatptorProperties;
    }

    @Override
    public AccountDto loginExistUser(LoginRequest loginRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<LoginRequest> requestHttpEntity = new HttpEntity<>(loginRequest,httpHeaders);
        ResponseEntity<AccountDto> exchange = restTemplate.exchange(
                accountAdatptorProperties.getUrlName() + "/user/login",
                HttpMethod.POST,
                requestHttpEntity,
                new ParameterizedTypeReference<AccountDto>() {
                }
        );
        if(!exchange.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException();
        }
        return exchange.getBody();
    }
}
