package com.example.librarybooking.controller;

import com.example.librarybooking.dto.BookDto;
import jakarta.validation.Valid;
import com.example.librarybooking.entity.Book;
import com.example.librarybooking.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200", "http://localhost:3000", "http://127.0.0.1:3000"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.PATCH})
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping({"/books", "/api/books"})
    public Book insertBook(@Valid @RequestBody BookDto bookDto) {

        return bookService.insertBook(bookDto);
    }

    @GetMapping({"/books", "/api/books"})
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping({"/books/available/count", "/api/books/available/count"})
    public long getAvailableBooksCount() {
        return bookService.getAvailableBooksCount();
    }

    @GetMapping({"/books/{id}", "/api/books/{id}"})
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);

    }

    @PutMapping({"/books/{id}", "/api/books/{id}"})
    public Book updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }
    @DeleteMapping({"/books/{id}", "/api/books/{id}"})
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
