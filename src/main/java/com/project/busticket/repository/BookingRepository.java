package com.project.busticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    boolean existsByUserId_UserId(String userId);

    boolean existsByTripId_TripId(String TripId);

    Booking findByBookingId(String bookingId);

    List<Booking> findByUserId_UserId(String userId);
}
