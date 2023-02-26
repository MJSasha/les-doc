package com.mjsasha.statisticservice.models;

import com.mjsasha.commonmodels.statistic.StatisticEvent;
import com.mjsasha.commonmodels.statistic.StatisticEventModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "statistics")
public class StatisticEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private StatisticEvent statisticEvent;
    private int lessonId;
    private String data;
    private Instant dateTimeOfCreate;

    // TODO - refactor (xml configuration)
    public StatisticEventEntity(StatisticEventModel statisticEventModel) {
        id = statisticEventModel.getId();
        statisticEvent = statisticEventModel.getStatisticEvent();
        lessonId = statisticEventModel.getLessonId();
        data = statisticEventModel.getData();
        dateTimeOfCreate = Instant.now();
    }
}
