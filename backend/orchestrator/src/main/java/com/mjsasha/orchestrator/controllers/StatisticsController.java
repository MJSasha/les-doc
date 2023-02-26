package com.mjsasha.orchestrator.controllers;

import com.mjsasha.commonmodels.statistic.StatisticEventModel;
import com.mjsasha.orchestrator.configs.ServicesProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private static final String CONTROLLER_URI = "/statistics";
    private final WebClient webClient;

    public StatisticsController(ServicesProperties servicesProperties) {
        webClient = WebClient.builder().baseUrl(servicesProperties.getStatisticOrigin()).build();
    }

    @GetMapping
    public List<StatisticEventModel> getAll() {
        return webClient
                .get().uri(CONTROLLER_URI)
                .retrieve().bodyToFlux(StatisticEventModel.class).toStream().toList();
    }
}
