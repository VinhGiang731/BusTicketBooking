package com.project.busticket.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class TripResponse {
    String tripId;
    String busOperatorId;

    String fromLocation;
    String toLocation;
    LocalDateTime departurTime;
    LocalDateTime arrivalTime;
    BigDecimal price;
    Integer totalSeats;
}
