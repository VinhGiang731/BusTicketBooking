package com.project.busticket.dto.request.trip;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
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
public class TripRequest {
    @NotNull(message = "NOT_NULL")
    String busOperatorId;
    @NotNull(message = "NOT_NULL")
    String fromLocation;
    @NotNull(message = "NOT_NULL")
    String toLocation;

    @NotNull(message = "NOT_NULL")
    @Future(message = "TIME_UNAVALIABLE")
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    LocalDateTime departurTime;

    @NotNull(message = "NOT_NULL")
    @Future(message = "TIME_UNAVALIABLE")
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    LocalDateTime arrivalTime;

    @NotNull(message = "NOT_NULL")
    @DecimalMin(value = "0.0", inclusive = false, message = "INVALID_NUMBER")
    BigDecimal price;

    @NotNull(message = "NOT_NULL")
    @Min(value = 1, message = "INVALID_NUMBER")
    Integer totalSeats;
}
