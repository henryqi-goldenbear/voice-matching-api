package com.henryqi.voicematching.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("profiles")
public class Profile {
    @Id
    private Long id;
    private String name;
    @Column("current_emotional_state")
    private String currentEmotionalState;
    @Column("current_life_chapter")
    private String currentLifeChapter;
    @Column("current_social_energy")
    private String currentSocialEnergy;
    @Column("last_check_in")
    private OffsetDateTime lastCheckIn;

    public Profile() {}

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
