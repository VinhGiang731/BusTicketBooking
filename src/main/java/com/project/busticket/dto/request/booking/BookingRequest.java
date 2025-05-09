package com.project.busticket.dto.request.booking;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "NOT_NULL")
    String userId;

    @NotNull(message = "NOT_NULL")
    String tripId;

    @NotNull(message = "NOT_NULL")
    @Pattern(regexp = "\\d+", message = "INVALID_NUMBER")
    Integer seats_number;

    @NotNull(message = "NOT_NULL")
    LocalDateTime bookingTime;
}
