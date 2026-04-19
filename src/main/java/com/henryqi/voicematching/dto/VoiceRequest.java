package com.henryqi.voicematching.dto;

public record VoiceRequest(
        Long profileId,
        String voiceAudioBase64,
        String transcript
) {}
