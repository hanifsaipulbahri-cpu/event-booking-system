package com.example.event_booking.service;

import com.example.event_booking.dto.AdminBookingResponse;
import com.example.event_booking.exception.ResourceNotFoundException;
import com.example.event_booking.model.Booking;
import com.example.event_booking.model.Event;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.BookingRepository;
import com.example.event_booking.repository.EventRepository;
import com.example.event_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<AdminBookingResponse> getAllBookingDetails() {

        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(booking -> {

            User user = userRepository.findById(booking.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            Event event = eventRepository.findById(booking.getEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

            return new AdminBookingResponse(
                    booking.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    event.getTitle(),
                    booking.getNumberOfSeats(),
                    booking.getTotalPrice(),
                    booking.getBookingStatus(),
                    booking.getBookingDate()
            );

        }).toList();
    }

    public Map<String, Object> getDashboardReport() {

        Map<String, Object> report = new HashMap<>();

        List<User> users = userRepository.findAll();
        List<Event> events = eventRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        long confirmedBookings = bookings.stream()
                .filter(b -> "CONFIRMED".equalsIgnoreCase(b.getBookingStatus()))
                .count();

        long cancelledBookings = bookings.stream()
                .filter(b -> "CANCELLED".equalsIgnoreCase(b.getBookingStatus()))
                .count();

        double totalRevenue = bookings.stream()
                .filter(b -> "CONFIRMED".equalsIgnoreCase(b.getBookingStatus()))
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        report.put("totalUsers", users.size());
        report.put("totalEvents", events.size());
        report.put("totalBookings", bookings.size());
        report.put("confirmedBookings", confirmedBookings);
        report.put("cancelledBookings", cancelledBookings);
        report.put("totalRevenue", totalRevenue);
        logger.info("Admin dashboard report generated.");

        return report;
    }
}