// src/main/java/com/example/razzie/repository/MovieRepository.java
package com.outsera.golden_raspberry_awards.repository;

import com.outsera.golden_raspberry_awards.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByWinnerTrue();
}