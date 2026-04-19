package com.henryqi.voicematching.repository;

import com.henryqi.voicematching.model.Experience;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExperienceRepository extends CrudRepository<Experience, Long> {
    @Query("select * from experiences")
    List<Experience> findAllExperiences();

    @Query("select * from experiences where container_type = :containerType limit 1")
    Experience findTopByContainerType(String containerType);
}
