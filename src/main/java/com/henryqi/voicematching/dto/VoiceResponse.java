package com.henryqi.voicematching.dto;

import com.henryqi.voicematching.model.Experience;
import com.henryqi.voicematching.model.MatchRecord;

public class VoiceResponse {
	private String message;
	private AiExtraction extracted;
	private MatchRecord newMatch;
	private Experience experience;

	public VoiceResponse() {}

	public VoiceResponse(String message, AiExtraction extracted, MatchRecord newMatch, Experience experience) {
		this.message = message;
		this.extracted = extracted;
		this.newMatch = newMatch;
		this.experience = experience;
	}

	public String getMessage() { return message; }
	public AiExtraction getExtracted() { return extracted; }
	public MatchRecord getNewMatch() { return newMatch; }
	public Experience getExperience() { return experience; }

	public String message() { return message; }
	public AiExtraction extracted() { return extracted; }
	public MatchRecord newMatch() { return newMatch; }
	public Experience experience() { return experience; }

	public void setMessage(String message) { this.message = message; }
	public void setExtracted(AiExtraction extracted) { this.extracted = extracted; }
	public void setNewMatch(MatchRecord newMatch) { this.newMatch = newMatch; }
	public void setExperience(Experience experience) { this.experience = experience; }
}
