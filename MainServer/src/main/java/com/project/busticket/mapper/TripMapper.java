package com.project.busticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.busticket.dto.request.trip.TripRequest;
import com.project.busticket.dto.response.TripResponse;
import com.project.busticket.entity.Trip;

@Mapper(componentModel = "spring")
public interface TripMapper {
    // anotation mapping busOperatorId ở request sang properties busOperatorId ở
    // khoá ngoại bên entity
    @Mapping(source = "busOperatorId", target = "busOperatorId.busOperatorId")
    Trip toTrip(TripRequest Request);

    @Mapping(source = "busOperatorId.busOperatorId", target = "busOperatorId")
    TripResponse toTripResponse(Trip trip);

    @Mapping(source = "busOperatorId", target = "busOperatorId.busOperatorId")
    void updateInfo(@MappingTarget Trip trip, TripResponse request);
}
