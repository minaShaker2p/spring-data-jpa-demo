package com.mina.springdata.service;

import com.mina.springdata.entity.Flight;
import com.mina.springdata.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class FlightService {

    @Autowired
    private final FlightRepository flightRepository;

    public void saveFLight(Flight flight) {
        flightRepository.save(flight);
        throw new RuntimeException("I Failed");
    }

    @Transactional
    public void saveFLightTransactional(Flight flight) {
        flightRepository.save(flight);
        throw new RuntimeException("I Failed");
    }
}
