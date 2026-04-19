package com.henryqi.voicematching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceRequest {
    private Long profileId;
    private String voiceAudioBase64;
    private String transcript;
}
