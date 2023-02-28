package com.mjsasha.orchestrator.controllers;

import com.mjsasha.commonmodels.statistic.StatisticEvent;
import com.mjsasha.commonmodels.statistic.StatisticEventModel;
import com.mjsasha.orchestrator.configs.ServicesProperties;
import com.mjsasha.orchestrator.kafka.StatisticProducer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/convertor")
public class ConvertorController {

    private static final String CONTROLLER_URI = "/convertor";
    private final WebClient webClient;
    private final StatisticProducer statisticProducer;

    public ConvertorController(ServicesProperties servicesProperties, StatisticProducer statisticProducer) {
        webClient = WebClient.builder().baseUrl(servicesProperties.getConvertorOrigin()).build();
        this.statisticProducer = statisticProducer;
    }

    @Operation(summary = "example")
    @GetMapping("/example")
    public ResponseEntity<String> example() {
        var response = ResponseEntity.ok(
                webClient
                        .get().uri(uriBuilder -> uriBuilder
                                .path(CONTROLLER_URI + "/example")
                                .build())
                        .retrieve().bodyToMono(String.class).block());
        if (response.getStatusCode().is2xxSuccessful()) {
            statisticProducer.sendEvent(new StatisticEventModel(StatisticEvent.FILE_UPLOADED));
        }
        return response;
    }
}