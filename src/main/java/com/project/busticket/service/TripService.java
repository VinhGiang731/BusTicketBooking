package com.project.busticket.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.trip.TripRequest;
import com.project.busticket.dto.response.TripResponse;
import com.project.busticket.entity.Trip;
import com.project.busticket.exception.Appexception;
import com.project.busticket.exception.ErrorCode;
import com.project.busticket.mapper.TripMapper;
import com.project.busticket.repository.TripRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripService {
    TripMapper tripMapper;
    TripRepository tripRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Trip createTrip(TripRequest request) {
        if (tripRepository.existsByBusOperatorId_BusOperatorId(request.getBusOperatorId())
                && tripRepository.existsByFromLocation(request.getFromLocation())
                && tripRepository.existsByDeparturTime(request.getDeparturTime()))
            throw new Appexception(ErrorCode.TRIP_INVALID);

        Trip trip = tripMapper.toTrip(request);
        return tripRepository.save(trip);
    }

    public List<TripResponse> getTrips() {
        return tripRepository.findAll().stream().map(tripMapper::toTripResponse).toList();
    }

    public TripResponse getTripById(String id) {
        return tripMapper.toTripResponse(
                tripRepository.findById(id).orElseThrow(() -> new Appexception(ErrorCode.TRIP_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deletdTrip(String id) {
        if (!tripRepository.existsById(id)) {
            return "Trip does not exist";
        } else {
            tripRepository.deleteById(id);
            return "Trip has been deleted";
        }
    }
}
