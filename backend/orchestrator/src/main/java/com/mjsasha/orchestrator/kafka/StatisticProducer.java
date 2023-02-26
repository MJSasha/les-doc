package com.mjsasha.orchestrator.kafka;

import com.mjsasha.commonmodels.statistic.StatisticEventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticProducer {

    private final KafkaTemplate<String, StatisticEventModel> kafkaTemplate;

    public StatisticProducer(KafkaTemplate<String, StatisticEventModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(StatisticEventModel statisticEventModel) {
        log.info(String.format("### Sent event -> %s", statisticEventModel));

        Message<StatisticEventModel> message = MessageBuilder.withPayload(statisticEventModel)
                .setHeader(KafkaHeaders.TOPIC, "statistic")
                .build();
        kafkaTemplate.send(message);
    }
}
