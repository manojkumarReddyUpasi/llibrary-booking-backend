package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.dto.BookDto;
import com.example.librarybooking.entity.Book;
import com.example.librarybooking.exception.ResourceNotFoundException;
import com.example.librarybooking.repository.BookRepository;
import com.example.librarybooking.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book insertBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailable(bookDto.getAvailable());
        book.setAccent( bookDto.getAccent());
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public long getAvailableBooksCount() {
        return bookRepository.countByAvailableTrue();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));

        return mapToDto(book);
    }

    @Override
    public Book updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailable(bookDto.getAvailable() != null ? bookDto.getAvailable() : book.getAvailable());
        book.setAccent(bookDto.getAccent());
        book.setBookCount(bookDto.getBookCount() != null ? bookDto.getBookCount() : book.getBookCount());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
                   throw new ResourceNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    private BookDto mapToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setCategory(book.getCategory());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setAccent(book.getAccent());
        return bookDto;
    }
}
