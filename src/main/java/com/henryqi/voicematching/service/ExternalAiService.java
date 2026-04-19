package com.henryqi.voicematching.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henryqi.voicematching.config.ExternalApiProperties;
import com.henryqi.voicematching.dto.AiExtraction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalAiService {

    private final RestTemplate restTemplate;
    private final ExternalApiProperties properties;
    private final ObjectMapper objectMapper;

    public ExternalAiService(RestTemplate restTemplate, ExternalApiProperties properties, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Async("voicePipelineExecutor")
    public CompletableFuture<String> transcribe(String base64Audio) {
        if (base64Audio == null || base64Audio.isBlank()) {
            return CompletableFuture.completedFuture(null);
        }
        if (properties.getElevenLabsApiKey() == null || properties.getElevenLabsApiKey().isBlank()) {
            return CompletableFuture.completedFuture("Error processing audio, fallback to default transcript.");
        }
        return CompletableFuture.supplyAsync(() -> "Transcription placeholder: integrate multipart ElevenLabs request here");
    }

    @Async("voicePipelineExecutor")
    public CompletableFuture<AiExtraction> extractProfileFromVoice(String transcript) {
        if (properties.getAnthropicApiKey() == null || properties.getAnthropicApiKey().isBlank()) {
            return CompletableFuture.completedFuture(new AiExtraction("overwhelmed", "transition", "Grounding"));
        }

        String prompt = "Extract emotional_state, life_chapter, social_energy as concise JSON from: " + transcript;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", properties.getAnthropicApiKey());
        headers.set("anthropic-version", "2023-06-01");

        Map<String, Object> body = Map.of(
                "model", "claude-3-5-sonnet-latest",
                "max_tokens", 200,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        try {
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            String response = restTemplate.postForObject(
                    "https://api.anthropic.com/v1/messages", request, String.class);
            JsonNode root = objectMapper.readTree(response);
            String text = root.path("content").get(0).path("text").asText();
            JsonNode parsed = objectMapper.readTree(text);
            return CompletableFuture.completedFuture(new AiExtraction(
                    parsed.path("emotional_state").asText("overwhelmed"),
                    parsed.path("life_chapter").asText("transition"),
                    parsed.path("social_energy").asText("Grounding")
            ));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AiExtraction("overwhelmed", "transition", "Grounding"));
        }
    }
}
