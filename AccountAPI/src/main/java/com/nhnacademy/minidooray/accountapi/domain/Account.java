package com.nhnacademy.minidooray.accountapi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Accounts")
public class Account {

    @Id
    private String userId;

    private String userPassword;

    private String userEmail;

    private UserState userState;

    public Account(String userId, String userPassword, String userEmail, UserState userState) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userState = userState;
    }
}
