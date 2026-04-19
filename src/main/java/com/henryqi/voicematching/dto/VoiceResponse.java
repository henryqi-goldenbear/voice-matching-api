package com.henryqi.voicematching.dto;

import com.henryqi.voicematching.model.Experience;
import com.henryqi.voicematching.model.MatchRecord;

public record VoiceResponse(
        String message,
        AiExtraction extracted,
        MatchRecord newMatch,
        Experience experience
) {}
