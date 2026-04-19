package com.henryqi.voicematching.repository;

import com.henryqi.voicematching.model.Circle;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface CircleRepository extends CrudRepository<Circle, Long> {
    @Query("select * from circles where experience_id = :experienceId and status = 'pending' limit 1")
    Circle findPendingByExperienceId(Long experienceId);
}
