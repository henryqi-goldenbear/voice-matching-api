package com.henryqi.voicematching.dto;

import com.henryqi.voicematching.model.Experience;
import com.henryqi.voicematching.model.MatchRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceResponse {
    private String message;
    private AiExtraction extracted;
    private MatchRecord newMatch;
    private Experience experience;
}
