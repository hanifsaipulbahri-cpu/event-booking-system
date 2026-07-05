package com.example.event_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponse {

    private String bookingId;

    private String eventTitle;

    private LocalDate eventDate;

    private String venue;

    private Integer numberOfSeats;

    private Double totalPrice;

    private String bookingStatus;

    private LocalDateTime bookingDate;

}