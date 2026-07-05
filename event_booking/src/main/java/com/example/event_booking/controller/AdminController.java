package com.example.event_booking.controller;

import com.example.event_booking.dto.AdminBookingResponse;
import com.example.event_booking.service.AdminService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public Object getUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/events")
    public Object getEvents() {
        return adminService.getAllEvents();
    }

    @GetMapping("/report")
    public Object getReport() {
        return adminService.getDashboardReport();
    }

    @GetMapping("/bookings")
    public List<AdminBookingResponse> getAllBookings() {
        return adminService.getAllBookingDetails();

    }
}