// src/main/java/com/example/razzie/repository/ProducerAwardRepository.java
package com.outsera.golden_raspberry_awards.repository;

import com.outsera.golden_raspberry_awards.model.ProducerAward;
import com.outsera.golden_raspberry_awards.model.dto.ProducerAwardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProducerAwardRepository extends JpaRepository<ProducerAward, Long> {

    @Query("SELECT pa.producer.name, pa.awardYear FROM ProducerAward pa ORDER BY pa.producer.name, pa.awardYear")
    List<ProducerAwardDTO> findProducerAwardsGroupedByProducer();
}