package com.mina.springdata;


import com.mina.springdata.entity.Flight;
import com.mina.springdata.repository.FlightRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class DerivedQueryTests {

    @Autowired
    private FlightRepository flightRepository;


    @Before
    public void setup() {
        flightRepository.deleteAll();
    }

    @Test
    public void shouldFindFlightFromLondon() {
        final Flight flight1 = createFlight("London");
        final Flight flight2 = createFlight("Egypt");
        final Flight flight3 = createFlight("Germany");

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        final List<Flight> result = flightRepository.findByOrigin("London");

        assertThat(result).hasSize(1);

        assertThat(result.get(0)).isEqualToComparingFieldByField(flight1);

    }

    @Test
    public void shouldFindFlightFromLondonToParis() {

        final Flight flight1 = createFlight("London", "Paris");
        final Flight flight2 = createFlight("London", "Assuit");
        final Flight flight3 = createFlight("London", "Elsharkya");


        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        final List<Flight> flightsFromLondonToParis =
                flightRepository.findByOriginAndDestination("London", "Paris");

        assertThat(flightsFromLondonToParis)
                .hasSize(1)
                .first()
                .isEqualToComparingFieldByField(flight1);
    }

    @Test
    public void shouldFindFlightFromLondonToParisCaseSensitive() {

        final Flight flight1 = createFlight("London");
        final Flight flight2 = createFlight("Egypt");
        final Flight flight3 = createFlight("Germany");


        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        final List<Flight> flightsFromLondonToParis =
                flightRepository.findByOriginIgnoreCase("LONDON");

        assertThat(flightsFromLondonToParis)
                .hasSize(1)
                .first()
                .isEqualToComparingFieldByField(flight1);
    }


    @Test
    public void shouldFIndFlightFromLondonOrMadrid() {

        final Flight flight1 = createFlight("London", "Paris");
        final Flight flight2 = createFlight("Madrid", "Assuit");
        final Flight flight3 = createFlight("Germany", "Elsharkya");


        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        final List<Flight> results = flightRepository.findByOriginIn("London", "Madrid");

        assertThat(results).hasSize(2);
        assertThat(results.get(0)).isEqualToComparingFieldByField(flight1);
        assertThat(results.get(1)).isEqualToComparingFieldByField(flight2);


    }


    private Flight createFlight(String origin, String destination) {
        final Flight flight = new Flight();
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setScheduledTime(LocalDateTime.parse("2020-12-13T12:12:00"));
        return flight;
    }

    private Flight createFlight(String origin) {
        final Flight flight = new Flight();
        flight.setOrigin(origin);
        flight.setDestination("Madrid");
        flight.setScheduledTime(LocalDateTime.parse("2020-12-13T12:12:00"));
        return flight;
    }
}
