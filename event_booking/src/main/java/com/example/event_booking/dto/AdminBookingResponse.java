package com.example.event_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminBookingResponse {

    private String bookingId;

    private String customerName;

    private String customerEmail;

    private String eventTitle;

    private Integer numberOfSeats;

    private Double totalPrice;

    private String bookingStatus;

    private LocalDateTime bookingDate;

}