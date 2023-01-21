package com.mjsasha.lesdoc;

import com.mjsasha.lesdoc.configs.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class LesDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(LesDocApplication.class, args);
    }

}
