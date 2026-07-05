package com.example.event_booking.controller;

import com.example.event_booking.dto.BookingRequest;
import com.example.event_booking.dto.BookingResponse;
import com.example.event_booking.model.Booking;
import com.example.event_booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookingController {

    private final BookingService bookingService;

        @PostMapping
        public Booking createBooking(
            @Valid @RequestBody BookingRequest request,
            Authentication authentication
        ) {

        return bookingService.createBooking(
                request,
                authentication.getName()
        );

    }

        @GetMapping("/my")
        public List<BookingResponse> myBookings(
                Authentication authentication
        ) {

        return bookingService.myBookings(
                authentication.getName()
        );

    }

        @DeleteMapping("/{bookingId}")
        public String cancelBooking(
                @PathVariable String bookingId,
                Authentication authentication
        ) {

        return bookingService.cancelBooking(
                bookingId,
                authentication.getName()
        );

        }

}