package com.henryqi.voicematching.dto;

public class AiExtraction {
	private String emotionalState;
	private String lifeChapter;
	private String socialEnergy;

	public AiExtraction() {}

	public AiExtraction(String emotionalState, String lifeChapter, String socialEnergy) {
		this.emotionalState = emotionalState;
		this.lifeChapter = lifeChapter;
		this.socialEnergy = socialEnergy;
	}

	public String getEmotionalState() { return emotionalState; }
	public String getLifeChapter() { return lifeChapter; }
	public String getSocialEnergy() { return socialEnergy; }

	// compatibility with code that calls record-style accessors
	public String emotionalState() { return emotionalState; }
	public String lifeChapter() { return lifeChapter; }
	public String socialEnergy() { return socialEnergy; }

	public void setEmotionalState(String emotionalState) { this.emotionalState = emotionalState; }
	public void setLifeChapter(String lifeChapter) { this.lifeChapter = lifeChapter; }
	public void setSocialEnergy(String socialEnergy) { this.socialEnergy = socialEnergy; }
}
