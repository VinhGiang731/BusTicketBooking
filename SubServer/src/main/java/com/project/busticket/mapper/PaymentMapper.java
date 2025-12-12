package com.project.busticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.busticket.dto.request.payment.PaymentRequest;
import com.project.busticket.dto.response.PaymentResponse;
import com.project.busticket.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "bookingId", target = "bookingId.bookingId")
    Payment toPayment(PaymentRequest request);

    @Mapping(source = "bookingId.bookingId", target = "bookingId")
    PaymentResponse toPaymentResponse(Payment request);
}
