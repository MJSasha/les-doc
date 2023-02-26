package com.mjsasha.statisticservice.kafka;

import com.mjsasha.commonmodels.statistic.StatisticEventModel;
import com.mjsasha.statisticservice.models.StatisticEventEntity;
import com.mjsasha.statisticservice.services.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticConsumer {

    private final StatisticService statisticService;

    public StatisticConsumer(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @KafkaListener(topics = "statistic", groupId = "statisticGroup")
    public void consume(StatisticEventModel statisticEventModel) {
        statisticService.create(new StatisticEventEntity(statisticEventModel));
        log.info(String.format("### Event received -> %s", statisticEventModel));
    }
}
