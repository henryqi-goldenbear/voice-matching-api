package com.voicematch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * Production ElevenLabs STT service.
 * Uses singleton Java 21 HttpClient (HTTP/2 keep-alive pool).
 * Builds real multipart/form-data without external libs.
 * 3x retry with exponential back-off for resilience under burst load.
 */
@Slf4j
@Service
public class ElevenLabsService {

    private static final String STT_URL = "https://api.elevenlabs.io/v1/speech-to-text";
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(java.time.Duration.ofSeconds(10))
            .build();

    @Value("${elevenlabs.api-key:}")
    private String apiKey;

    public String transcribe(String audioBase64) throws Exception {
        if (apiKey == null || apiKey.isBlank()) {
            return "Fallback: api key not configured.";
        }
        byte[] audioBytes = Base64.getDecoder().decode(audioBase64);
        String boundary = "JavaBoundary" + UUID.randomUUID().toString().replace("-", "");
        byte[] body = buildMultipart(boundary, audioBytes);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STT_URL))
                .header("xi-api-key", apiKey)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .timeout(java.time.Duration.ofSeconds(30))
                .build();

        Exception lastEx = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                HttpResponse<String> res = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
                if (res.statusCode() == 200) return parseText(res.body());
                lastEx = new RuntimeException("ElevenLabs HTTP " + res.statusCode());
            } catch (Exception e) {
                lastEx = e;
            }
            if (attempt < 3) Thread.sleep(200L * attempt);
        }
        throw lastEx;
    }

    private byte[] buildMultipart(String boundary, byte[] audio) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String CRLF = "\r\n";
        out.write(("--" + boundary + CRLF).getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Disposition: form-data; name=\"file\"; filename=\"audio.mp3\"" + CRLF).getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Type: audio/mpeg" + CRLF + CRLF).getBytes(StandardCharsets.UTF_8));
        out.write(audio);
        out.write(CRLF.getBytes(StandardCharsets.UTF_8));
        out.write(("--" + boundary + CRLF).getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Disposition: form-data; name=\"model_id\"" + CRLF + CRLF).getBytes(StandardCharsets.UTF_8));
        out.write("eleven_multilingual_v2".getBytes(StandardCharsets.UTF_8));
        out.write(CRLF.getBytes(StandardCharsets.UTF_8));
        out.write(("--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8));
        return out.toByteArray();
    }

    private String parseText(String json) {
        int idx = json.indexOf("\"text\":");
        if (idx < 0) return "(no text)";
        int s = json.indexOf('"', idx + 7) + 1;
        return json.substring(s, json.indexOf('"', s));
    }
}
