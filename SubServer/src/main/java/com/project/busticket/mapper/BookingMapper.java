package com.project.busticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.busticket.dto.request.booking.BookingRequest;
import com.project.busticket.dto.response.BookingResponse;
import com.project.busticket.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "userId", target = "userId.userId")
    @Mapping(source = "tripId", target = "tripId.tripId")
    Booking toBooking(BookingRequest request);

    @Mapping(source = "userId.userId", target = "userId")
    @Mapping(source = "tripId.tripId", target = "tripId")
    BookingResponse toBookingResponse(Booking booking);

    @Mapping(source = "userId", target = "userId.userId")
    @Mapping(source = "tripId", target = "tripId.tripId")
    void updateInfo(@MappingTarget Booking booking, BookingRequest request);

}
