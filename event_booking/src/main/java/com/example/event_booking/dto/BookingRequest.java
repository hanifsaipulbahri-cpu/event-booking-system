package com.example.event_booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingRequest {

    @NotBlank
    private String eventId;

    @Min(1)
    private Integer numberOfSeats;

}