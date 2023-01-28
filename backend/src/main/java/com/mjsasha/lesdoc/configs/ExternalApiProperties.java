package com.mjsasha.lesdoc.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "external-api")
public class ExternalApiProperties {

    String apiKey;
}
