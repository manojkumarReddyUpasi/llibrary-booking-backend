package com.example.librarybooking.service;

import com.example.librarybooking.dto.ReservationDto;
import com.example.librarybooking.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation insertReservation(ReservationDto reservationDto);
    void deleteReservation(Long id);
    List<Reservation> getAllReservations();
}
