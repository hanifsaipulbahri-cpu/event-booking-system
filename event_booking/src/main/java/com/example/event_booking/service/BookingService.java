package com.example.event_booking.service;

import com.example.event_booking.dto.BookingRequest;
import com.example.event_booking.dto.BookingResponse;
import com.example.event_booking.exception.BadRequestException;
import com.example.event_booking.exception.ResourceNotFoundException;
import com.example.event_booking.exception.UnauthorizedException;
import com.example.event_booking.model.Booking;
import com.example.event_booking.model.Event;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.BookingRepository;
import com.example.event_booking.repository.EventRepository;
import com.example.event_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    public Booking createBooking(
            BookingRequest request,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (event.getSeatsAvailable() < request.getNumberOfSeats()) {
            throw new BadRequestException("Not enough seats available");
        }

        event.setSeatsAvailable(
                event.getSeatsAvailable() - request.getNumberOfSeats()
        );

        eventRepository.save(event);

        Booking booking = Booking.builder()
                .userId(user.getId())
                .eventId(event.getId())
                .numberOfSeats(request.getNumberOfSeats())
                .bookingDate(LocalDateTime.now())
                .bookingStatus("CONFIRMED")
                .totalPrice(event.getPrice() * request.getNumberOfSeats())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        logger.info(
                "User {} booked {} seat(s) for event {}",
                email,
                request.getNumberOfSeats(),
                event.getTitle()
        );

        return savedBooking;

    }

        public List<BookingResponse> myBookings(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Booking> bookings =
                bookingRepository.findByUserId(user.getId());

        return bookings.stream()
                .map(booking -> {
                        Event event = eventRepository.findById(booking.getEventId())
                                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

                        return new BookingResponse(
                                booking.getId(),
                                event.getTitle(),
                                event.getEventDate(),
                                event.getVenue(),
                                booking.getNumberOfSeats(),
                                booking.getTotalPrice(),
                                booking.getBookingStatus(),
                                booking.getBookingDate()
                        );
                })
                .collect(Collectors.toList());

        }

    public String cancelBooking(String bookingId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // User can only cancel their own booking
        if (!booking.getUserId().equals(user.getId())) {
                throw new UnauthorizedException("You are not allowed to cancel this booking");
        }

        // Booking already cancelled
        if ("CANCELLED".equalsIgnoreCase(booking.getBookingStatus())) {
                throw new BadRequestException("Booking already cancelled");
        }

        Event event = eventRepository.findById(booking.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // Return seats
        event.setSeatsAvailable(
                event.getSeatsAvailable() + booking.getNumberOfSeats()
        );

        eventRepository.save(event);

        // Update booking status
        booking.setBookingStatus("CANCELLED");
        bookingRepository.save(booking);

        logger.info(
                "Booking {} cancelled by {}",
                bookingId,
                email
        );

        return "Booking cancelled successfully";
        }
}