package com.project.busticket.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.payment.PaymentRequest;
import com.project.busticket.dto.response.PaymentResponse;
import com.project.busticket.entity.Payment;
import com.project.busticket.mapper.PaymentMapper;
import com.project.busticket.repository.PaymentRepository;

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
    @Autowired
    BookingService bookingService;

    public Payment createPayment(PaymentRequest request) {
        request.setAmount(bookingService.totalPrices(request.getBookingId()));
        Payment payment = paymentMapper.toPayment(request);
        return paymentRepository.save(payment);
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
