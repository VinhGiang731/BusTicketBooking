package com.project.busticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.busticket.dto.request.booking.BookingRequest;
import com.project.busticket.dto.response.ApiResponse;
import com.project.busticket.dto.response.BookingDetailResponse;
import com.project.busticket.entity.Booking;
import com.project.busticket.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService service;

    @PostMapping
    ApiResponse<Booking> createBooking(@RequestBody @Valid BookingRequest request) {
        return ApiResponse.<Booking>builder()
                .result(service.createBooking(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<BookingDetailResponse>> getBookingList() {
        return ApiResponse.<List<BookingDetailResponse>>builder()
                .result(service.getListTicket())
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<String> deleteBooking(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(service.deletedBooking(id))
                .build();
    }
}
