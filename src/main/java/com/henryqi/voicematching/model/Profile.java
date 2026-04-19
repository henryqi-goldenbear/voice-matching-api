package com.henryqi.voicematching.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("profiles")
public class Profile {
    @Id
    private Long id;
    private String name;
    private String currentEmotionalState;
    private String currentLifeChapter;
    private String currentSocialEnergy;
    private OffsetDateTime lastCheckIn;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCurrentEmotionalState() { return currentEmotionalState; }
    public void setCurrentEmotionalState(String currentEmotionalState) { this.currentEmotionalState = currentEmotionalState; }
    public String getCurrentLifeChapter() { return currentLifeChapter; }
    public void setCurrentLifeChapter(String currentLifeChapter) { this.currentLifeChapter = currentLifeChapter; }
    public String getCurrentSocialEnergy() { return currentSocialEnergy; }
    public void setCurrentSocialEnergy(String currentSocialEnergy) { this.currentSocialEnergy = currentSocialEnergy; }
    public OffsetDateTime getLastCheckIn() { return lastCheckIn; }
    public void setLastCheckIn(OffsetDateTime lastCheckIn) { this.lastCheckIn = lastCheckIn; }
}
