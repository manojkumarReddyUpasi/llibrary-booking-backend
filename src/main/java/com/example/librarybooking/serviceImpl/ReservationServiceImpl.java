package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.dto.ReservationDto;
import com.example.librarybooking.entity.Book;
import com.example.librarybooking.entity.Reservation;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.exception.ResourceNotFoundException;
import com.example.librarybooking.repository.BookRepository;
import com.example.librarybooking.repository.ReservationRepository;
import com.example.librarybooking.repository.UserRepository;
import com.example.librarybooking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Reservation insertReservation(ReservationDto reservationDto) {
        User useEn = userRepository.findById(reservationDto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User with id " + reservationDto.getUserId() + " not found"));
        Book book = bookRepository.findById(reservationDto.getBookId())
                        .orElseThrow(() -> new ResourceNotFoundException("Book with id " + reservationDto.getBookId() + " not found"));
        Reservation reservation = new Reservation();
        reservation.setUser(useEn);
        reservation.setBook(book);
        reservation.setIssueDate(reservationDto.getIssueDate());
        reservation.setDueDate(reservationDto.getDueDate());
        reservation.setReturnDate(reservationDto.getReturnDate());
        reservation.setStatus(Reservation.ReservationStatus.ACTIVE);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with id " + id + " not found"));
        reservationRepository.deleteById(id);
    }

}
