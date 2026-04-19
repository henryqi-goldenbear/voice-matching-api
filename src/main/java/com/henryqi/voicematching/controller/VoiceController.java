package com.henryqi.voicematching.controller;

import com.henryqi.voicematching.dto.VoiceResponse;
import com.henryqi.voicematching.dto.VoiceRequest;
import com.henryqi.voicematching.service.VoiceProcessingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voice")
public class VoiceController {

    private final VoiceProcessingService voiceProcessingService;

    public VoiceController(VoiceProcessingService voiceProcessingService) {
        this.voiceProcessingService = voiceProcessingService;
    }

    @PostMapping
    public VoiceResponse process(@RequestBody VoiceRequest request) {
        return voiceProcessingService.processVoice(request);
    }
}
