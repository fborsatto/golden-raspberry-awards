package com.outsera.golden_raspberry_awards.repository;

import com.outsera.golden_raspberry_awards.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(String name);
}