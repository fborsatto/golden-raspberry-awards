package com.outsera.golden_raspberry_awards.config;

import com.outsera.golden_raspberry_awards.service.MovieService;
import com.outsera.golden_raspberry_awards.service.ProducerAwardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class DataBaseLoader implements ApplicationRunner {

    private final MovieService movieService;
    private final ProducerAwardService producerAwardService;

    @Value("classpath:static/Movielist.csv")
    private Resource movielistCsv;

    public DataBaseLoader(MovieService movieService, ProducerAwardService producerAwardService) {
        this.movieService = movieService;
        this.producerAwardService = producerAwardService;
    }

    @Override
    public void run(ApplicationArguments args) {
        loadMoviesFromCsv();
        producerAwardService.loadProducerAwards();
    }

    private void loadMoviesFromCsv() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(movielistCsv.getInputStream()))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                movieService.saveMovie(line);
            }
        }
        catch (Exception e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
}