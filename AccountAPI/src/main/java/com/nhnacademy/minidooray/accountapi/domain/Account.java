package com.nhnacademy.minidooray.accountapi.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Account")
@Entity
public class Account {

    @Id
    private String userId;

    private String userPassword;

    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Nullable
    private UserState userState;

    public Account(String userId, String userPassword, String userEmail, UserState userState) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userState = userState;
    }

    public Account(String userId, String userPassword, String userEmail) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }
}
