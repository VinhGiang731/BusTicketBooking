package com.project.busticket.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, String> {

    // spring sẽ tự hiểu tìm theo id
    boolean existsByBusOperatorId_BusOperatorId(String busOperatorId);

    boolean existsByFromLocation(String fromLocation);

    boolean existsByDeparturTime(LocalDateTime departurTime);

    Optional<Trip> findByTripId(String tripId);
}
