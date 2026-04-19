package com.henryqi.voicematching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VoiceMatchingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoiceMatchingApiApplication.class, args);
    }
}
