package com.mjsasha.statisticservice.repositories;

import com.mjsasha.statisticservice.models.StatisticEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEventEntity, Long> {
}
