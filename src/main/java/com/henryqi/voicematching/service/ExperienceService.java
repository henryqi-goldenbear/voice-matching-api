package com.henryqi.voicematching.service;

import com.henryqi.voicematching.model.Experience;
import com.henryqi.voicematching.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> getAll() {
        return experienceRepository.findAllExperiences();
    }
}
