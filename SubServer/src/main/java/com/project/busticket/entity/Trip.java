package com.project.busticket.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String tripId;

    // khoá ngoại với BusOperator
    @ManyToOne
    @JoinColumn(name = "busOperatorId", nullable = false)
    BusOperator busOperatorId;

    String fromLocation;
    String toLocation;
    LocalDateTime departurTime;
    LocalDateTime arrivalTime;
    BigDecimal price;
    Integer totalSeats;
}
