package com.project.busticket.dto.response;

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
public class BookingDetailResponse {
    String bookingId;
    String busOperatorName;
    String contactPhone;
    String fromLocation;
    String toLocation;
    LocalDateTime departurTime;
    LocalDateTime arrivalTime;
    Integer seats_number;
    LocalDateTime bookingTime;
}
