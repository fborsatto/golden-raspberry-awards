package com.outsera.golden_raspberry_awards.controller;

import com.outsera.golden_raspberry_awards.model.response.AwardIntervalsResponse;
import com.outsera.golden_raspberry_awards.service.ProducerAwardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awards")
public class AwardController {

    private final ProducerAwardService awardService;

    AwardController(ProducerAwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/intervals")
    public ResponseEntity<AwardIntervalsResponse> getAwardIntervals() {
        AwardIntervalsResponse response = awardService.getProducerIntervals();
        return ResponseEntity.ok(response);
    }
}