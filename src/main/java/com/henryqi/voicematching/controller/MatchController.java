package com.henryqi.voicematching.controller;

import com.henryqi.voicematching.dto.MatchStatusUpdateRequest;
import com.henryqi.voicematching.model.MatchRecord;
import com.henryqi.voicematching.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches/{profileId}")
    public List<MatchRecord> getMatches(@PathVariable Long profileId) {
        return matchService.getMatchesByProfileId(profileId);
    }

    @PatchMapping("/matches/{id}")
    public MatchRecord updateMatch(@PathVariable Long id, @RequestBody MatchStatusUpdateRequest request) {
        return matchService.updateStatus(id, request.status());
    }
}
