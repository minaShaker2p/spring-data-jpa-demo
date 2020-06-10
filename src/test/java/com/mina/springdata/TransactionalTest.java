package com.mina.springdata;

import com.mina.springdata.entity.Flight;
import com.mina.springdata.repository.FlightRepository;
import com.mina.springdata.service.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Before
    public void setUp() {
        flightRepository.deleteAll();
    }

    @Test
    public void shouldNotRollBackWhenThereNoTransaction() {
        try {
            flightService.saveFLight(new Flight());

        } catch (Exception ex) {
            //Do Nothing

        } finally {
            assertThat(flightRepository.findAll())
                    .isNotEmpty();
        }
    }

    @Test
    public void shouldNotRollBackWhenThereIsTransaction() {
        try {
            flightService.saveFLightTransactional(new Flight());

        } catch (Exception ex) {
            //Do Nothing

        } finally {
            assertThat(flightRepository.findAll())
                    .isEmpty();
        }
    }

}
