package com.mjsasha.orchestrator.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDoc {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("LesDoc API")
                        .version("v1.0.1")
                        .license(new License().name("GitHub").url("https://github.com/MJSasha/les-doc")));
    }
}
