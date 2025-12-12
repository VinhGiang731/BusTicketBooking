package com.project.busticket.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.booking.BookingRequest;
import com.project.busticket.dto.response.BookingDetailResponse;
import com.project.busticket.dto.response.BusOperatorResponse;
import com.project.busticket.dto.response.TripResponse;
import com.project.busticket.entity.Booking;
import com.project.busticket.entity.Users;
import com.project.busticket.exception.Appexception;
import com.project.busticket.exception.ErrorCode;
import com.project.busticket.mapper.BookingMapper;
import com.project.busticket.mapper.BusOperatorMapper;
import com.project.busticket.mapper.TripMapper;
import com.project.busticket.repository.BookingRepository;
import com.project.busticket.repository.BusOperatorRepository;
import com.project.busticket.repository.TripRepository;
import com.project.busticket.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    UserRepository userRepository;
    TripRepository tripRepository;
    TripMapper tripMapper;
    BusOperatorRepository busOperatorRepository;
    BusOperatorMapper busOperatorMapper;
    HttpServletRequest httprequest;

    @CacheEvict(value = "userTickets", key = "#request.userId", condition = "#request.userId != null")
    public Booking createBooking(BookingRequest request) {
        String userId = getUserId();
        request.setUserId(userId);
        log.info("userid {}", userId);
        if (!userRepository.existsByUserId(userId))
            throw new Appexception(ErrorCode.USER_NOT_EXISTED);
        if (!tripRepository.existsById(request.getTripId()))
            throw new Appexception(ErrorCode.TRIP_NOT_EXISTED);

        Booking booking = bookingMapper.toBooking(request);
        return bookingRepository.save(booking);
    }

    @CacheEvict(value = "userTickets", allEntries = true)
    public String deletedBooking(String id) {
        if (!bookingRepository.existsById(id)) {
            return "Booking not found";
        } else {
            bookingRepository.deleteById(id);
            return "Booking deleted";
        }
    }

    @Cacheable(value = "userIdsBySession", key = "#httprequest.session.id", unless = "#result == null")
    public String getUserId() {
        HttpSession session = httprequest.getSession(false);
        String name = session.getAttribute("userName").toString();
        List<Users> userList = userRepository.findByUserName(name);
        if (userList.isEmpty())
            throw new Appexception(ErrorCode.USER_NOT_EXISTED);
        return userList.get(0).getUserId();
    }

    @Cacheable(value = "userTickets", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()", unless = "#result == null or #result.isEmpty()")
    public List<BookingDetailResponse> getListTicket() {
        List<BookingDetailResponse> bookingList = new ArrayList<>();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        List<Users> userList = userRepository.findByUserName(name);
        if (userList.isEmpty())
            throw new Appexception(ErrorCode.USER_NOT_EXISTED);

        Users user = userList.get(0);
        String userId = user.getUserId();
        log.info("Userlogin: {}", userId);
        List<Booking> booking = bookingRepository.findByUserId_UserId(userId);

        log.info("Booking: {}", booking);
        if (booking.isEmpty())
            throw new Appexception(ErrorCode.UNCATEGORIZED_EXCEPTION);

        for (var bk : booking) {
            BookingDetailResponse detail = new BookingDetailResponse();
            detail.setBookingId(bk.getBookingId());

            TripResponse triprs = tripMapper.toTripResponse(tripRepository.findByTripId(bk.getTripId().getTripId())
                    .orElseThrow(() -> new Appexception(ErrorCode.TRIP_NOT_EXISTED)));

            BusOperatorResponse busOpeRs = busOperatorMapper
                    .toBusOperatorResponse(busOperatorRepository.findByBusOperatorId(triprs.getBusOperatorId())
                            .orElseThrow(() -> new Appexception(ErrorCode.BUS_UN_EXISTED)));

            detail.setBusOperatorName(busOpeRs.getBusOperatorName());
            detail.setContactPhone(busOpeRs.getContactPhone());
            detail.setFromLocation(triprs.getFromLocation());
            detail.setToLocation(triprs.getToLocation());
            detail.setDeparturTime(triprs.getDeparturTime());
            detail.setArrivalTime(triprs.getArrivalTime());
            detail.setSeats_number(bk.getSeats_number());
            detail.setBookingTime(bk.getBookingTime());

            bookingList.add(detail);
        }

        return bookingList;
    }

    public BigDecimal totalPrices(BigDecimal price, Integer seats) {
        return price.multiply(BigDecimal.valueOf(seats));
    }
}
