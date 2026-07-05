package com.example.event_booking.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    private String userId;

    private String eventId;

    private Integer numberOfSeats;

    private Double totalPrice;

    private String bookingStatus;

    private LocalDateTime bookingDate;

}