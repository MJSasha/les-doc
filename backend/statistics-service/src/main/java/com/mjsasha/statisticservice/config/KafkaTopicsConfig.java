package com.mjsasha.statisticservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("statistic").build();
    }

    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
