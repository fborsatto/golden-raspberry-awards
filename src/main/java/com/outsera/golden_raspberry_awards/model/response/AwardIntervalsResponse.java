package com.outsera.golden_raspberry_awards.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AwardIntervalsResponse {
    private List<ProducerInterval> min;
    private List<ProducerInterval> max;

    @Builder
    @Data
    public static class ProducerInterval {
        private String producer;
        private Integer interval;
        private Integer previousWin;
        private Integer followingWin;
    }
}