package com.henryqi.voicematching.service;

import com.henryqi.voicematching.model.Circle;
import com.henryqi.voicematching.model.Experience;
import com.henryqi.voicematching.model.MatchRecord;
import com.henryqi.voicematching.repository.CircleRepository;
import com.henryqi.voicematching.repository.ExperienceRepository;
import com.henryqi.voicematching.repository.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final CircleRepository circleRepository;
    private final ExperienceRepository experienceRepository;

    public MatchService(MatchRepository matchRepository,
                        CircleRepository circleRepository,
                        ExperienceRepository experienceRepository) {
        this.matchRepository = matchRepository;
        this.circleRepository = circleRepository;
        this.experienceRepository = experienceRepository;
    }

    public List<MatchRecord> getMatchesByProfileId(Long profileId) {
        List<MatchRecord> matches = matchRepository.findByProfileId(profileId);
        for (MatchRecord match : matches) {
            Circle circle = circleRepository.findById(match.getCircleId()).orElse(null);
            match.setCircle(circle);
            if (circle != null) {
                Experience experience = experienceRepository.findById(circle.getExperienceId()).orElse(null);
                match.setExperience(experience);
            }
        }
        return matches;
    }

    public MatchRecord updateStatus(Long id, String status) {
        MatchRecord match = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
        match.setStatus(status);
        return matchRepository.save(match);
    }

    public MatchRecord upsert(Long profileId, Long circleId, String reason) {
        MatchRecord existing = matchRepository.findByProfileIdAndCircleId(profileId, circleId);
        if (existing != null) {
            existing.setStatus("invited");
            existing.setMatchReason(reason);
            return matchRepository.save(existing);
        }
        MatchRecord match = new MatchRecord();
        match.setProfileId(profileId);
        match.setCircleId(circleId);
        match.setStatus("invited");
        match.setMatchReason(reason);
        return matchRepository.save(match);
    }
}
