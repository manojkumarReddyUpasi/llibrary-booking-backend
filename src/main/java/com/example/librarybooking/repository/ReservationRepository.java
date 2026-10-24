package com.example.librarybooking.repository;

import com.example.librarybooking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{
    @EntityGraph(attributePaths = {"user.roles", "book"})
    @Query("select distinct r from Reservation r")
    List<Reservation> findAllWithDetails();
}
