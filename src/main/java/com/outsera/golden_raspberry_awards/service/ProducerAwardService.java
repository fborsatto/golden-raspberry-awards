package com.outsera.golden_raspberry_awards.service;

import com.outsera.golden_raspberry_awards.model.Movie;
import com.outsera.golden_raspberry_awards.model.ProducerAward;
import com.outsera.golden_raspberry_awards.model.dto.ProducerAwardDTO;
import com.outsera.golden_raspberry_awards.model.response.AwardIntervalsResponse;
import com.outsera.golden_raspberry_awards.repository.MovieRepository;
import com.outsera.golden_raspberry_awards.repository.ProducerAwardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProducerAwardService {

    private final MovieRepository movieRepository;
    private final ProducerAwardRepository producerAwardRepository;

    ProducerAwardService(MovieRepository movieRepository, ProducerAwardRepository producerAwardRepository) {
        this.movieRepository = movieRepository;
        this.producerAwardRepository = producerAwardRepository;
    }

    @Transactional
    public void loadProducerAwards() {
        List<Movie> winners = movieRepository.findByWinnerTrue();

        winners.forEach(movie -> movie.getProducers().forEach(
                producer -> producerAwardRepository.save(ProducerAward.builder().producer(producer).awardYear(movie.getReleaseYear()).build())));
    }

    public AwardIntervalsResponse getProducerIntervals() {

        List<ProducerAwardDTO> producerAwardsGroupedByProducer = producerAwardRepository.findProducerAwardsGroupedByProducer();

        HashMap<String, List<Integer>> awardsMap = new HashMap<>();

        producerAwardsGroupedByProducer.forEach(producerAwardDTO -> {
            if (!awardsMap.containsKey(producerAwardDTO.getProducer())) {
                awardsMap.put(producerAwardDTO.getProducer(), new ArrayList<>());
            }
            awardsMap.get(producerAwardDTO.getProducer()).add(producerAwardDTO.getAwardYear());
        });

        List<AwardIntervalsResponse.ProducerInterval> maxInterval = calculateMaxInterval(awardsMap);
        List<AwardIntervalsResponse.ProducerInterval> minInterval = calculateMinInterval(awardsMap);

        return AwardIntervalsResponse.builder()
                .max(maxInterval)
                .min(minInterval)
                .build();
    }

    private List<AwardIntervalsResponse.ProducerInterval> calculateMaxInterval(HashMap<String, List<Integer>> awardsMap) {

        final List<AwardIntervalsResponse.ProducerInterval> maxIntervalList = new ArrayList<>();

        awardsMap.forEach((producer, awards) -> {
            int previousInterval = awards.getFirst();
            for (int i = 1; i < awards.size(); i++) {
                int interval = awards.get(i) - previousInterval;
                previousInterval = awards.get(i);
                if (maxIntervalList.isEmpty() || interval >= maxIntervalList.getFirst().getInterval()) {
                    if (!maxIntervalList.isEmpty() && interval > maxIntervalList.getFirst().getInterval()) {
                        maxIntervalList.clear();
                    }
                    maxIntervalList.add(AwardIntervalsResponse.ProducerInterval.builder()
                            .followingWin(awards.get(i))
                            .previousWin(awards.get(i-1))
                            .producer(producer)
                            .interval(interval)
                            .build());
                }
            }
        });

        return maxIntervalList;
    }

    private List<AwardIntervalsResponse.ProducerInterval> calculateMinInterval(HashMap<String, List<Integer>> awardsMap) {

        final List<AwardIntervalsResponse.ProducerInterval> minIntervalList = new ArrayList<>();

        awardsMap.forEach((producer, awards) -> {
            int previousInterval = awards.getFirst();
            for (int i = 1; i < awards.size(); i++) {
                int interval = awards.get(i) - previousInterval;
                previousInterval = awards.get(i);
                if (minIntervalList.isEmpty() || interval <= minIntervalList.getFirst().getInterval()) {
                    if (!minIntervalList.isEmpty() && interval < minIntervalList.getFirst().getInterval()) {
                        minIntervalList.clear();
                    }
                    minIntervalList.add(AwardIntervalsResponse.ProducerInterval.builder()
                            .followingWin(awards.get(i))
                            .previousWin(awards.get(i-1))
                            .producer(producer)
                            .interval(interval)
                            .build());
                }
            }
        });

        return minIntervalList;
    }


}
