package com.mjsasha.statisticservice.controllers;

import com.mjsasha.statisticservice.data.models.StatisticEventEntity;
import com.mjsasha.statisticservice.services.StatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticService statisticService;

    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping
    public List<StatisticEventEntity> getAll() {
        return statisticService.getAll();
    }
}
