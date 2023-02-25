package com.mjsasha.commonmodels.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatisticEventModel {

    private Long id;
    private StatisticEvent statisticEvent;
    private int lessonId;

    public StatisticEventModel(StatisticEvent statisticEvent, int lessonId) {
        this.statisticEvent = statisticEvent;
        this.lessonId = lessonId;
    }
}
