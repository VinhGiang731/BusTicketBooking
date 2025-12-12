package com.project.busticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.busticket.dto.request.trip.TripRequest;
import com.project.busticket.dto.response.ApiResponse;
import com.project.busticket.dto.response.TripResponse;
import com.project.busticket.entity.Trip;
import com.project.busticket.service.TripService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/trip")
@Slf4j
public class TripController {
    @Autowired
    TripService service;

    @GetMapping
    ApiResponse<List<TripResponse>> getTrips() {
        log.info("Get Trip form SUB_SERVER");
        return ApiResponse.<List<TripResponse>>builder()
                .result(service.getTrips())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<TripResponse> getTripById(@PathVariable String id) {
        return ApiResponse.<TripResponse>builder()
                .result(service.getTripById(id))
                .build();
    }

    @PostMapping
    ApiResponse<Trip> createTrip(@RequestBody @Valid TripRequest request) {
        return ApiResponse.<Trip>builder()
                .result(service.createTrip(request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteTrip(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(service.deletdTrip(id))
                .build();
    }
}
