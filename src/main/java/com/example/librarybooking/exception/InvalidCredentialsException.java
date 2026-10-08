package com.example.librarybooking.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid email, password, or account type");
    }
}
