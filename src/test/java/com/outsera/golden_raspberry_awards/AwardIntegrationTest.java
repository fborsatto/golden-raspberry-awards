package com.outsera.golden_raspberry_awards;

import com.outsera.golden_raspberry_awards.model.response.AwardIntervalsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwardIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnCorrectAwardIntervals() {
        ResponseEntity<AwardIntervalsResponse> response = restTemplate.getForEntity("/awards/intervals", AwardIntervalsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        AwardIntervalsResponse awards = response.getBody();

        assertThat(awards.getMin())
                .isNotEmpty()
                .hasSize(1)
                .element(0)
                .satisfies(producer -> {
                    assertThat(producer.getProducer()).isEqualTo("Joel Silver");
                    assertThat(producer.getInterval()).isEqualTo(1);
                    assertThat(producer.getPreviousWin()).isEqualTo(1990);
                    assertThat(producer.getFollowingWin()).isEqualTo(1991);
                });

        assertThat(awards.getMax())
                .isNotEmpty()
                .hasSize(1)
                .element(0)
                .satisfies(producer -> {
                    assertThat(producer.getProducer()).isEqualTo("Matthew Vaughn");
                    assertThat(producer.getInterval()).isEqualTo(13);
                    assertThat(producer.getPreviousWin()).isEqualTo(2002);
                    assertThat(producer.getFollowingWin()).isEqualTo(2015);
                });
    }
}