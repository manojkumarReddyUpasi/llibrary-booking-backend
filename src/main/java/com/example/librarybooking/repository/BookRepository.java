package com.example.librarybooking.repository;

import com.example.librarybooking.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> {
    long countByAvailableTrue();
}
