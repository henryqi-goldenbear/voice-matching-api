package com.henryqi.voicematching.service;

import com.henryqi.voicematching.dto.AiExtraction;
import com.henryqi.voicematching.dto.VoiceRequest;
import com.henryqi.voicematching.dto.VoiceResponse;
import com.henryqi.voicematching.model.Circle;
import com.henryqi.voicematching.model.Experience;
import com.henryqi.voicematching.model.Profile;
import com.henryqi.voicematching.model.VoiceSession;
import com.henryqi.voicematching.repository.CircleRepository;
import com.henryqi.voicematching.repository.ExperienceRepository;
import com.henryqi.voicematching.repository.VoiceSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class VoiceProcessingService {

    private final ExternalAiService externalAiService;
    private final ProfileService profileService;
    private final VoiceSessionRepository voiceSessionRepository;
    private final ExperienceRepository experienceRepository;
    private final CircleRepository circleRepository;
    private final MatchService matchService;

    public VoiceProcessingService(ExternalAiService externalAiService,
                                  ProfileService profileService,
                                  VoiceSessionRepository voiceSessionRepository,
                                  ExperienceRepository experienceRepository,
                                  CircleRepository circleRepository,
                                  MatchService matchService) {
        this.externalAiService = externalAiService;
        this.profileService = profileService;
        this.voiceSessionRepository = voiceSessionRepository;
        this.experienceRepository = experienceRepository;
        this.circleRepository = circleRepository;
        this.matchService = matchService;
    }

    @Transactional
    public VoiceResponse processVoice(VoiceRequest request) {
        if (request.profileId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "profileId is required");
        }

        String finalTranscript = request.transcript();
        if ((finalTranscript == null || finalTranscript.isBlank()) && request.voiceAudioBase64() != null) {
            finalTranscript = externalAiService.transcribe(request.voiceAudioBase64()).join();
        }
        if (finalTranscript == null || finalTranscript.isBlank()) {
            finalTranscript = "I am feeling overwhelmed and need grounding.";
        }

        CompletableFuture<AiExtraction> extractionFuture = externalAiService.extractProfileFromVoice(finalTranscript);
        AiExtraction extraction = extractionFuture.join();

        VoiceSession session = new VoiceSession();
        session.setProfileId(request.profileId());
        session.setTranscript(finalTranscript);
        session.setExtractedEmotion(extraction.emotionalState());
        session.setExtractedChapter(extraction.lifeChapter());
        session.setExtractedEnergy(extraction.socialEnergy());
        voiceSessionRepository.save(session);

        Profile profile = profileService.getById(request.profileId());
        profile.setCurrentEmotionalState(extraction.emotionalState());
        profile.setCurrentLifeChapter(extraction.lifeChapter());
        profile.setCurrentSocialEnergy(extraction.socialEnergy());
        profile.setLastCheckIn(OffsetDateTime.now());
        profileService.save(profile);

        String containerType = null;
        if (extraction.socialEnergy() != null && extraction.socialEnergy().contains("Grounding")) {
            containerType = "Grounding";
        } else if (extraction.socialEnergy() != null && extraction.socialEnergy().contains("Celebration")) {
            containerType = "Celebratory";
        }

        Experience experience = containerType == null
                ? experienceRepository.findAllExperiences().stream().findFirst().orElse(null)
                : experienceRepository.findTopByContainerType(containerType);

        if (experience == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No experiences available to match right now.");
        }

        Circle circle = circleRepository.findPendingByExperienceId(experience.getId());
        if (circle == null) {
            circle = new Circle();
            circle.setExperienceId(experience.getId());
            circle.setStatus("pending");
            circle = circleRepository.save(circle);
        }

        var match = matchService.upsert(
                request.profileId(),
                circle.getId(),
                "This " + experience.getContainerType() + " event matches your " + extraction.emotionalState() + " state."
        );

        return new VoiceResponse(
                "Voice processed and matching initialized",
                extraction,
                match,
                experience
        );
    }
}
