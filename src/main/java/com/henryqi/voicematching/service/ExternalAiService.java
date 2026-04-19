package com.henryqi.voicematching.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henryqi.voicematching.config.ExternalApiProperties;
import com.henryqi.voicematching.dto.AiExtraction;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalAiService {

    private final RestClient restClient;
    private final ExternalApiProperties properties;
    private final ObjectMapper objectMapper;

    public ExternalAiService(RestClient restClient, ExternalApiProperties properties, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Async("ioExecutor")
    public CompletableFuture<String> transcribe(String base64Audio) {
        if (base64Audio == null || base64Audio.isBlank()) {
            return CompletableFuture.completedFuture(null);
        }
        if (properties.getElevenLabsApiKey() == null || properties.getElevenLabsApiKey().isBlank()) {
            return CompletableFuture.completedFuture("Error processing audio, fallback to default transcript.");
        }
        return CompletableFuture.supplyAsync(() -> "Transcription placeholder: integrate multipart ElevenLabs request here");
    }

    @Async("ioExecutor")
    public CompletableFuture<AiExtraction> extractProfileFromVoice(String transcript) {
        if (properties.getAnthropicApiKey() == null || properties.getAnthropicApiKey().isBlank()) {
            return CompletableFuture.completedFuture(new AiExtraction("overwhelmed", "transition", "Grounding"));
        }

        String prompt = "Extract emotional_state, life_chapter, social_energy as concise JSON from: " + transcript;

        String response = restClient.post()
                .uri("https://api.anthropic.com/v1/messages")
                .header("x-api-key", properties.getAnthropicApiKey())
                .header("anthropic-version", "2023-06-01")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "model", "claude-3-5-sonnet-latest",
                        "max_tokens", 200,
                        "messages", new Object[]{Map.of("role", "user", "content", prompt)}
                ))
                .retrieve()
                .body(String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            String text = root.path("content").get(0).path("text").asText("{}");
            JsonNode extracted = objectMapper.readTree(text);
            return CompletableFuture.completedFuture(new AiExtraction(
                    extracted.path("emotional_state").asText("overwhelmed"),
                    extracted.path("life_chapter").asText("transition"),
                    extracted.path("social_energy").asText("Grounding")
            ));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AiExtraction("overwhelmed", "transition", "Grounding"));
        }
    }
}
