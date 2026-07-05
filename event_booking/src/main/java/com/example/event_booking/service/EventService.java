package com.example.event_booking.service;

import com.example.event_booking.exception.ResourceNotFoundException;
import com.example.event_booking.model.Event;
import com.example.event_booking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public Event createEvent(Event event) {

        Event savedEvent = repository.save(event);

        logger.info("New event created: {}", savedEvent.getTitle());

        return savedEvent;
    }

    public Event getEvent(String id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found"));

    }

    public Event updateEvent(String id, Event event) {

        Event existingEvent = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found"));

        event.setId(existingEvent.getId());

        Event updatedEvent = repository.save(event);

        logger.info("Event updated: {}", updatedEvent.getTitle());

        return updatedEvent;

    }

    public void deleteEvent(String id) {

        Event event = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found"));

        repository.delete(event);
        logger.info("Event deleted: {}", event.getTitle());

    }

    public Page<Event> getEvents(
            String keyword,
            String category,
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        boolean hasKeyword =
                keyword != null && !keyword.isBlank();

        boolean hasCategory =
                category != null && !category.isBlank();

        if (hasKeyword && hasCategory) {
            return repository.findByTitleContainingIgnoreCaseAndCategoryIgnoreCase(
                    keyword,
                    category,
                    pageable
            );
        }

        if (hasKeyword) {
            return repository.findByTitleContainingIgnoreCase(
                    keyword,
                    pageable
            );
        }

        if (hasCategory) {
            return repository.findByCategoryIgnoreCase(
                    category,
                    pageable
            );
        }

        return repository.findAll(pageable);

    }

    public List<Event> searchEvents(String keyword, String category) {

        if (keyword != null && !keyword.isBlank()) {
                return repository.findByTitleContainingIgnoreCase(keyword);
        }

        if (category != null && !category.isBlank()) {
                return repository.findByCategoryIgnoreCase(category);
        }

        return repository.findAll();
        }

}