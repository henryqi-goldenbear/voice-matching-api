package com.henryqi.voicematching.repository;

import com.henryqi.voicematching.model.VoiceSession;
import org.springframework.data.repository.CrudRepository;

public interface VoiceSessionRepository extends CrudRepository<VoiceSession, Long> {
}
