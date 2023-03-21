package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedule, Integer> {

    Schedule findByName(String name);
}
