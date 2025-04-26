package com.project.busticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, String> {

}
