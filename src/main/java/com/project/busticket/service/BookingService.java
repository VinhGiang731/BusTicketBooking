package com.project.busticket.service;

import org.springframework.stereotype.Service;

import com.project.busticket.mapper.BookingMapper;
import com.project.busticket.repository.BookingRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {
    BookingMapper bookingMapper;
    BookingRepository bookingRepository;
}
