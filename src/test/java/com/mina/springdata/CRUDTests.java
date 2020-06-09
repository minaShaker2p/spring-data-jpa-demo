package com.mina.springdata;

import com.mina.springdata.entity.Flight;
import com.mina.springdata.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
class CRUDTests {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void shouldPerformCRUDOperation() {

        final Flight flight = new Flight();
        flight.setOrigin("Germany");
        flight.setDestination("Egypt");
        flight.setScheduledTime(LocalDateTime.parse("2020-12-13T12:12:00"));

        flightRepository.save(flight);


        assertThat(flightRepository.findAll())
                .hasSize(1)
                .first().isEqualTo(flight);

        flightRepository.deleteById(flight.getId());

        assertThat(flightRepository.count()).isZero();
    }

}
