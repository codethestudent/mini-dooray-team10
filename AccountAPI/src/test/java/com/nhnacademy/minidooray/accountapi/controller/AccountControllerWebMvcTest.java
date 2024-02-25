package com.nhnacademy.minidooray.accountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.accountapi.domain.Account;
import com.nhnacademy.minidooray.accountapi.domain.UserState;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
class AccountControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @Order(1)
    void getAccount() throws Exception {;

        given(accountService.getAccount("test")).willReturn(new Account("test", "12345", "123@123.com", UserState.ACTIVE));

        mockMvc.perform(get("/user/get/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value("test"));
    }

    @Test
    @Order(2)
    void getAccountError() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/user/get/user").session(session))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(3)
    void getAccounts() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "test");

        given(accountService.getAccounts()).willReturn(List.of(new Account("test", "12345", "123@123.com", UserState.DISABLED)));

        mockMvc.perform(get("/user/get/users").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value("test"));

    }

    @Test
    @Order(4)
    void testCreateAccount() throws Exception {
        Account init = new Account("test", "12345", "123@1.com", UserState.ACTIVE);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                post("/user/create")
                        .content(objectMapper.writeValueAsString(init))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value("test"));
    }

    @Test
    @Order(5)
    void deleteAccount() throws Exception {
        mockMvc.perform(delete("/user/{userId}", "test"))
                .andExpect(status().isOk());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.result", equalTo("OK")));
    }

    @Test
    @Order(6)
    void loginAccount() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "test");

        // Replace with a valid JSON representation of an Account object
        String requestBody = "{\"userId\":\"test\", \"userPassword\":\"12345\"}";

        mockMvc.perform(post("/user/login")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value("test"));
    }

    @Test
    @Order(7)
    void logout() throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "test");

        mockMvc.perform(get("/user/logout").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result_code").value(0));
    }
}