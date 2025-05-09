package com.project.busticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    boolean existsByUserId_UserId(String userId);

    boolean existsByTripId_TripId(String TripId);
}
