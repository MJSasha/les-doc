package com.mjsasha.commonmodels.statistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatisticEventModel {

    private Long id;
    private StatisticEvent statisticEvent;
    private int lessonId;
    private String data;
    private Instant dateTimeOfCreate;

    public StatisticEventModel(StatisticEvent statisticEvent, int lessonId) {
        this.statisticEvent = statisticEvent;
        this.lessonId = lessonId;
    }

    public StatisticEventModel(StatisticEvent statisticEvent) {
        this.statisticEvent = statisticEvent;
    }

    public StatisticEventModel(StatisticEvent statisticEvent, String data) {
        this.statisticEvent = statisticEvent;
        this.data = data;
    }

    public StatisticEventModel(StatisticEvent statisticEvent, int lessonId, String data) {
        this.statisticEvent = statisticEvent;
        this.lessonId = lessonId;
        this.data = data;
    }
}
