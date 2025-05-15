package com.project.busticket.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.payment.PaymentRequest;
import com.project.busticket.dto.response.BookingResponse;
import com.project.busticket.dto.response.PaymentResponse;
import com.project.busticket.dto.response.TripResponse;
import com.project.busticket.entity.Payment;
import com.project.busticket.exception.Appexception;
import com.project.busticket.exception.ErrorCode;
import com.project.busticket.mapper.BookingMapper;
import com.project.busticket.mapper.PaymentMapper;
import com.project.busticket.mapper.TripMapper;
import com.project.busticket.repository.BookingRepository;
import com.project.busticket.repository.PaymentRepository;
import com.project.busticket.repository.TripRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;
    BookingMapper bookingMapper;
    BookingRepository bookingRepository;
    TripMapper tripMapper;
    TripRepository tripRepository;

    @Autowired
    BookingService bookingService;

    @Autowired
    TripService tripService;

    public Map<String, Object> createPayment(PaymentRequest request) {
        Map<String, Object> response = new HashMap<>();

        BookingResponse booking = bookingMapper
                .toBookingResponse(bookingRepository.findByBookingId(request.getBookingId()));
        TripResponse trip = tripMapper.toTripResponse(tripRepository.findByTripId(booking.getTripId())
                .orElseThrow(() -> new Appexception(ErrorCode.TRIP_NOT_EXISTED)));

        request.setAmount(
                bookingService.totalPrices(trip.getPrice(), booking.getSeats_number()));

        Payment payment = paymentMapper.toPayment(request);

        paymentRepository.save(payment);
        response.put("payment", payment);

        trip.setTotalSeats(trip.getTotalSeats() - booking.getSeats_number());

        response.put("Trip Changed", tripService.updateTrip(trip.getTripId(), trip));

        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PaymentResponse> listPayment() {
        return paymentRepository.findAll().stream().map(paymentMapper::toPaymentResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BigDecimal getTotalAmount() {
        return paymentRepository.calculateTotalAmount();
    }
}
