package com.nhnacademy.minidooray.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "com.nhnacademy.account")
@Data
public class AccountAdatptorProperties {

    @NotNull
    private String urlName;

}
