package com.mjsasha.statisticservice.models;

import com.mjsasha.commonmodels.models.StatisticEvent;
import com.mjsasha.commonmodels.models.StatisticEventModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    public StatisticEventEntity(StatisticEventModel statisticEventModel) {
        id = statisticEventModel.getId();
        statisticEvent = statisticEventModel.getStatisticEvent();
        lessonId = statisticEventModel.getLessonId();
    }
}
