package com.mjsasha.orchestrator.kafka;

import com.mjsasha.orchestrator.models.StatisticEventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticProducer {

    private final KafkaTemplate<String, StatisticEventModel> kafkaTemplate;

    public StatisticProducer(KafkaTemplate<String, StatisticEventModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(StatisticEventModel statisticEventModel) {
        kafkaTemplate.send("statistic", statisticEventModel);
        log.info(String.format("### Sent event -> %s", statisticEventModel));
    }
}
