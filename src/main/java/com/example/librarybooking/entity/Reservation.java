package com.example.librarybooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "book_id", nullable = false)
        private Book book;

        @Column(name = "issue_date", nullable = false)
        private LocalDate issueDate;

        @Column(name = "return_date")
        private LocalDate returnDate;

        @Column(name = "due_date", nullable = false)
        private LocalDate dueDate;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ReservationStatus status;

        // getters and setters



    public enum ReservationStatus {
        ACTIVE, RETURNED
    }

        public ReservationStatus getStatus() {
                return status;
        }

        public LocalDate getReturnDate() {
                return returnDate;
        }

        public LocalDate getIssueDate() {
                return issueDate;
        }

        public Book getBook() {
                return book;
        }

        public User getUser() {
                return user;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public void setBook(Book book) {
                this.book = book;
        }

        public void setIssueDate(LocalDate issueDate) {
                this.issueDate = issueDate;
        }

        public void setReturnDate(LocalDate returnDate) {
                this.returnDate = returnDate;
        }

        public void setStatus(ReservationStatus status) {
                this.status = status;
        }

        public LocalDate getDueDate() {
                return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
                this.dueDate = dueDate;
        }
}
