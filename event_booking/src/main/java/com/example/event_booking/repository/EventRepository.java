package com.example.event_booking.repository;

import com.example.event_booking.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByTitleContainingIgnoreCase(String title);
    List<Event> findByCategoryIgnoreCase(String category);

    Page<Event> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Page<Event> findByCategoryIgnoreCase(
            String category,
            Pageable pageable
    );

    Page<Event> findByTitleContainingIgnoreCaseAndCategoryIgnoreCase(
            String keyword,
            String category,
            Pageable pageable
    );

}