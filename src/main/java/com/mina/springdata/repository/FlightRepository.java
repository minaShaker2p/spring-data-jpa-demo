package com.mina.springdata.repository;


import com.mina.springdata.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FlightRepository extends PagingAndSortingRepository<Flight, Long> {
    List<Flight> findByOrigin(String london);

    List<Flight> findByOriginAndDestination(String origin, String destination);

    List<Flight> findByOriginIn(String... origins);

    List<Flight> findByOriginIgnoreCase(String london);


    Page<Flight> findByOrigin(String origin, Pageable pageRequest);
}
