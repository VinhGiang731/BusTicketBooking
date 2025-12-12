package com.project.busticket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.project.busticket.dto.request.payment.PaymentRequest;

@Controller
public class WebsocketController {

    @MessageMapping("/send-message")
    @SendTo("/topic/notification")
    public PaymentRequest send(PaymentRequest request) {
        return request;
    }
}
