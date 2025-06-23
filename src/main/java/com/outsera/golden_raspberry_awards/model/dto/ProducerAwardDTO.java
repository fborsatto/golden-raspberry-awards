package com.outsera.golden_raspberry_awards.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerAwardDTO {
    private String producer;
    private Integer awardYear;
}
