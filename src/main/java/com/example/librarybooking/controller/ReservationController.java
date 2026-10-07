package com.example.librarybooking.controller;

import com.example.librarybooking.dto.ReservationDto;
import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.Reservation;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.service.ReservationService;
import com.example.librarybooking.service.UserRoleService;
import com.example.librarybooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
            ("/reservations")
    public Reservation insertReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.insertReservation(reservationDto);
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();

    }
    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
