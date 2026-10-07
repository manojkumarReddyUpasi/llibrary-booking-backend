package com.example.librarybooking.service;

import com.example.librarybooking.dto.BookDto;
import com.example.librarybooking.entity.Book;

import java.util.List;

public interface BookService {
    Book insertBook(BookDto bookDto);
    List<Book> getAllBooks();
    long getAvailableBooksCount();
    BookDto getBookById(Long id);
    Book updateBook(Long id, BookDto bookDto);
    void deleteBook(Long id);
}
