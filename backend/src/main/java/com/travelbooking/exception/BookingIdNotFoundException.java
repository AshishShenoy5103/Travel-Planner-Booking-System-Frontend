package com.travelbooking.exception;

public class BookingIdNotFoundException extends RuntimeException {
    public BookingIdNotFoundException(String message) {
        super(message);
    }
}
