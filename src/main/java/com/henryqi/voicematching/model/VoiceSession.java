package com.henryqi.voicematching.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("voice_sessions")
public class VoiceSession {
    @Id
    private Long id;
    private Long profileId;
    private String transcript;
    private String extractedEmotion;
    private String extractedChapter;
    private String extractedEnergy;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }
    public String getTranscript() { return transcript; }
    public void setTranscript(String transcript) { this.transcript = transcript; }
    public String getExtractedEmotion() { return extractedEmotion; }
    public void setExtractedEmotion(String extractedEmotion) { this.extractedEmotion = extractedEmotion; }
    public String getExtractedChapter() { return extractedChapter; }
    public void setExtractedChapter(String extractedChapter) { this.extractedChapter = extractedChapter; }
    public String getExtractedEnergy() { return extractedEnergy; }
    public void setExtractedEnergy(String extractedEnergy) { this.extractedEnergy = extractedEnergy; }
}
