package com.example.event_booking.repository;

import com.example.event_booking.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking,String> {

    List<Booking> findByUserId(String userId);

}