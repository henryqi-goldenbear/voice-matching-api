package com.henryqi.voicematching.dto;

public class VoiceRequest {
	private Long profileId;
	private String voiceAudioBase64;
	private String transcript;

	public VoiceRequest() {}

	public VoiceRequest(Long profileId, String voiceAudioBase64, String transcript) {
		this.profileId = profileId;
		this.voiceAudioBase64 = voiceAudioBase64;
		this.transcript = transcript;
	}

	public Long getProfileId() { return profileId; }
	public String getVoiceAudioBase64() { return voiceAudioBase64; }
	public String getTranscript() { return transcript; }

	public Long profileId() { return profileId; }
	public String voiceAudioBase64() { return voiceAudioBase64; }
	public String transcript() { return transcript; }

	public void setProfileId(Long profileId) { this.profileId = profileId; }
	public void setVoiceAudioBase64(String voiceAudioBase64) { this.voiceAudioBase64 = voiceAudioBase64; }
	public void setTranscript(String transcript) { this.transcript = transcript; }
}
