package com.henryqi.voicematching.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalApiProperties {

    @Value("${elevenlabs.api.key:}")
    private String elevenLabsApiKey;

    @Value("${anthropic.api.key:}")
    private String anthropicApiKey;

    public String getElevenLabsApiKey() {
        return elevenLabsApiKey;
    }

    public String getAnthropicApiKey() {
        return anthropicApiKey;
    }
}
