package com.henryqi.voicematching.repository;

import com.henryqi.voicematching.model.MatchRecord;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<MatchRecord, Long> {
    @Query("select * from matches where profile_id = :profileId")
    List<MatchRecord> findByProfileId(Long profileId);

    @Query("select * from matches where profile_id = :profileId and circle_id = :circleId limit 1")
    MatchRecord findByProfileIdAndCircleId(Long profileId, Long circleId);
}
