package com.mina.springdata;

import com.mina.springdata.entity.Flight;
import com.mina.springdata.repository.FlightRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataMongoTest
public class PagingAndSortingTests {

    @Autowired
    private FlightRepository flightRepository;


    @Before
    public void setup() {
        flightRepository.deleteAll();
    }

    @Test
    public void shouldSortFlightsByDestination() {
        final Flight madrid = createFlight("Madrid");
        final Flight paris = createFlight("Paris");
        final Flight london = createFlight("London");

        flightRepository.save(madrid);
        flightRepository.save(paris);
        flightRepository.save(london);

        final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination"));

        assertThat(flights).hasSize(3);

        final Iterator<Flight> iterator = flights.iterator();

        assertThat(iterator.next()).isEqualToComparingFieldByField(london);
        assertThat(iterator.next()).isEqualToComparingFieldByField(madrid);
        assertThat(iterator.next()).isEqualToComparingFieldByField(paris);
    }


    @Test
    public void shouldSortFlightByScheduledAndThenName() {
        final LocalDateTime now = LocalDateTime.now();

        final Flight paris1 = createFlight("Paris", now);
        final Flight paris2 = createFlight("Paris", now.plusHours(2));
        final Flight paris3 = createFlight("Paris", now.minusMinutes(30));
        final Flight london1 = createFlight("London", now);
        final Flight london2 = createFlight("London", now.plusHours(2));


        flightRepository.save(paris1);
        flightRepository.save(paris2);
        flightRepository.save(paris3);
        flightRepository.save(london1);
        flightRepository.save(london2);

        final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination", "scheduledTime"));
        assertThat(flights).hasSize(5);

        final Iterator<Flight> result = flights.iterator();

        assertThat(result.next()).isEqualToComparingFieldByField(london1);
        assertThat(result.next()).isEqualToComparingFieldByField(london2);
        assertThat(result.next()).isEqualToComparingFieldByField(paris3);
        assertThat(result.next()).isEqualToComparingFieldByField(paris1);
        assertThat(result.next()).isEqualToComparingFieldByField(paris2);

    }

    @Test
    public void showPageResults() {

        for (int i = 0; i < 50; i++) {
            flightRepository.save(createFlight(String.valueOf(i)));
        }

        final Page<Flight> page = flightRepository.findAll(PageRequest.of(2, 5));

        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent())
                .extracting(Flight::getDestination)
                .containsExactly("10", "11", "12", "13", "14");
    }

    @Test
    public void showPageAndSortResults() {

        for (int i = 0; i < 50; i++) {
            flightRepository.save(createFlight(String.valueOf(i)));
        }

        final Page<Flight> page = flightRepository.findAll(PageRequest.of(2, 5, Sort.by(Sort.Direction.DESC, "destination")));

        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent())
                .extracting(Flight::getDestination)
                .containsExactly("44", "43", "42", "41", "40");
    }

    @Test
    public void showPageAndSortADerivedQuery() {

        for (int i = 0; i < 10; i++) {
            Flight flight = createFlight(String.valueOf(i));
            flight.setOrigin("Paris");
            flightRepository.save(flight);
        }

        for (int i = 0; i < 10; i++) {
            Flight flight = createFlight(String.valueOf(i));
            flight.setOrigin("London");
            flightRepository.save(flight);
        }

        final Page<Flight> page = flightRepository.findByOrigin("London", PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "destination")));

        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent())
                .extracting(Flight::getDestination)
                .containsExactly("9", "8", "7", "6", "5");
    }

    private Flight createFlight(String destination, LocalDateTime scheduledTime) {
        final Flight flight = new Flight();
        flight.setOrigin("Egypt");
        flight.setDestination(destination);
        flight.setScheduledTime(scheduledTime);
        return flight;
    }

    private Flight createFlight(String destination) {
        final Flight flight = new Flight();
        flight.setOrigin("Egypt");
        flight.setDestination(destination);
        flight.setScheduledTime(LocalDateTime.parse("2020-12-13T12:12:00"));
        return flight;
    }

}
