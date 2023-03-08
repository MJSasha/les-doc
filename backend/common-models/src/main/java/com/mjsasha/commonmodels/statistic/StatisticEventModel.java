package com.mjsasha.commonmodels.statistic;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class StatisticEventModel {

    private Long id;
    private StatisticEvent statisticEvent;
    private int lessonId;
    private String data;
    private Instant dateTimeOfCreate;
}
