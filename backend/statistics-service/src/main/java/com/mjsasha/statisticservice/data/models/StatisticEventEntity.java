package com.mjsasha.statisticservice.data.models;

import com.mjsasha.statisticservice.data.definitions.StatisticEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
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
}
