package com.outsera.golden_raspberry_awards.service;

import com.outsera.golden_raspberry_awards.model.Movie;
import com.outsera.golden_raspberry_awards.model.Producer;
import com.outsera.golden_raspberry_awards.repository.MovieRepository;
import com.outsera.golden_raspberry_awards.repository.ProducerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;

    MovieService(MovieRepository movieRepository, ProducerRepository producerRepository) {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
    }

    @Transactional
    public void saveMovie(String line) {
            String[] values = line.split(";");
            Integer year = Integer.valueOf(values[0].trim());
            String title = values[1].trim();
            String studios = values[2].trim();
            String producersString = values[3].trim();
            Set<Producer> producers = buildProducersList(producersString);
            Boolean winner = values.length > 4 && values[4].trim().equalsIgnoreCase("yes");

            Movie movie = Movie.builder()
                    .releaseYear(year)
                    .title(title)
                    .studios(studios)
                    .winner(winner)
                    .producers(producers)
                    .build();
            movieRepository.save(movie);
    }

    private Set<Producer> buildProducersList(String producersString) {
        Set<Producer> producers = new HashSet<>();

        producersString = producersString.replace("and", ",");

        String[] producerNames = producersString.split(",");

        for (String name : producerNames) {
            String producerName = name.trim();
            Producer producer = producerRepository.findByName(producerName)
                    .orElseGet(() -> producerRepository.save(Producer.builder()
                            .name(producerName)
                            .build()));
            producers.add(producer);
        }

        return producers;
    }

}
