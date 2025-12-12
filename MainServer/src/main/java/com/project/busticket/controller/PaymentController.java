package com.project.busticket.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.busticket.dto.request.payment.PaymentRequest;
import com.project.busticket.dto.response.ApiResponse;
import com.project.busticket.dto.response.PaymentResponse;
import com.project.busticket.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService service;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/record")
    ApiResponse<Map<String, Object>> createPayment(@RequestBody PaymentRequest request) {
        return ApiResponse.<Map<String, Object>>builder()
                .result(service.createPayment(request))
                .build();
    }

    @GetMapping("/list/record")
    ApiResponse<List<PaymentResponse>> getListRecord() {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(service.listPayment())
                .build();
    }

    @GetMapping("/total/amount")
    ApiResponse<BigDecimal> totalAmount() {
        return ApiResponse.<BigDecimal>builder()
                .result(service.getTotalAmount())
                .build();
    }

    @PostMapping("/server/send-message")
    public void sendMessage(@RequestBody PaymentRequest request) {
        simpMessagingTemplate.convertAndSend("/topic/notification", request);
    }
}
