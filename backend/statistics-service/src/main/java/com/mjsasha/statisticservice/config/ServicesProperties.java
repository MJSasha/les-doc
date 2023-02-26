package com.mjsasha.statisticservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "services")
public class ServicesProperties {

    private String orchestratorOrigin;
}