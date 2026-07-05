package com.example.event_booking.controller;

import com.example.event_booking.model.Event;
import com.example.event_booking.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin("*")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Event> getEvents(

            @RequestParam(defaultValue = "") String keyword,

            @RequestParam(defaultValue = "") String category,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "6") int size,

            @RequestParam(defaultValue = "eventDate") String sortBy

    ) {

        return service.getEvents(
                keyword,
                category,
                page,
                size,
                sortBy
        );

    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable String id) {
        return service.getEvent(id);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return service.createEvent(event);
    }

    @PutMapping("/{id}")
    public Event updateEvent(
            @PathVariable String id,
            @RequestBody Event event
    ) {
        return service.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable String id) {
        service.deleteEvent(id);
    }

}