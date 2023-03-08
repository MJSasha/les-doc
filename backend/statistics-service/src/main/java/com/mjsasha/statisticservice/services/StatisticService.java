package com.mjsasha.statisticservice.services;

import com.mjsasha.statisticservice.data.models.StatisticEventEntity;
import com.mjsasha.statisticservice.repositories.StatisticRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public List<StatisticEventEntity> getAll() {
        return statisticRepository.findAll();
    }

    public void create(StatisticEventEntity statisticEventEntity) {
        statisticEventEntity.setDateTimeOfCreate(Instant.now());
        statisticRepository.save(statisticEventEntity);
    }
}
