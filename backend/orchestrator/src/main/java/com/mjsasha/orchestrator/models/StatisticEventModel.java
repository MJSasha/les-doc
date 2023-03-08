package com.mjsasha.orchestrator.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
