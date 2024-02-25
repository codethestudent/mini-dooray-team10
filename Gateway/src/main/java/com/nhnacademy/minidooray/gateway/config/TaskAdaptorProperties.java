package com.nhnacademy.minidooray.gateway.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "com.nhnacademy.task")
@Data
public class TaskAdaptorProperties {

    @NotBlank
    String urlName;

}
