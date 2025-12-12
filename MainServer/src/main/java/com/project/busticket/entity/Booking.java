package com.project.busticket.entity;

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
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String bookingId;
    // khoá ngoại với user
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    Users userId;

    // khoá ngoại với trip
    @ManyToOne
    @JoinColumn(name = "tripId", nullable = false)
    Trip tripId;

    Integer seats_number;
    LocalDateTime bookingTime;
}
