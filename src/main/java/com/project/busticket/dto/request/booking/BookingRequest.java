package com.project.busticket.dto.request.booking;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    String userId;

    @NotNull(message = "NOT_NULL")
    String tripId;

    @NotNull(message = "NOT_NULL")
    @Min(value = 1, message = "INVALID_NUMBER")
    Integer seats_number;

    @NotNull(message = "NOT_NULL")
    LocalDateTime bookingTime = LocalDateTime.now();
}
