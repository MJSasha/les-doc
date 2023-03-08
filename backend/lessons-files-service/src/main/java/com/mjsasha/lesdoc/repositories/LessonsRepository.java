package com.mjsasha.lesdoc.repositories;

import com.mjsasha.lesdoc.data.entities.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonsRepository extends CrudRepository<Lesson, Integer> {
}
