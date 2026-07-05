package com.example.event_booking.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collection="events")
public class Event {

    @Id
    private String id;

    private String title;

    private String description;

    private String category;

    private String venue;

    private LocalDate eventDate;

    private Double price;

    private Integer capacity;

    private Integer seatsAvailable;

    private String status;

    private LocalDateTime createdAt;

    private String imageUrl;
}