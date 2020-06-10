package com.mina.springdata;

import com.mina.springdata.entity.Flight;
import com.mina.springdata.repository.FlightRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomImplementTests {

    @Autowired
    private FlightRepository flightRepository;


    @Test
    public void shouldSaveCustomImplement() {
        final Flight toDelete = createFlight("London");
        final Flight toKeep = createFlight("Paris");
        flightRepository.save(toDelete);
        flightRepository.save(toKeep);

        flightRepository.deleteByOrigin("London");

        assertThat(flightRepository.findAll())
                .hasSize(1)
                .first()
                .isEqualToComparingFieldByField(toKeep);

    }

    private Flight createFlight(String origin) {
        final Flight flight = new Flight();
        flight.setOrigin(origin);
        flight.setDestination("Germany");
        flight.setScheduledTime(LocalDateTime.parse("2020-12-13T12:12:00"));
        return flight;
    }
}
