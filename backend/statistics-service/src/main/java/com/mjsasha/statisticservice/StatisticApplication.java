package com.mjsasha.statisticservice;

import com.mjsasha.statisticservice.config.ServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        ServicesProperties.class
})
public class StatisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class);
    }
}
