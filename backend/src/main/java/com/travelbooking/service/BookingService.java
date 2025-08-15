package com.travelbooking.service;

import com.travelbooking.dto.BookingDTO;
import com.travelbooking.dto.BookingResponseDTO;
import com.travelbooking.model.Booking;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDto, String email);
    List<BookingResponseDTO> getAllBookings(String email);
    BookingResponseDTO getBooking(Long id, String email);
    void deleteBookingById(Long id, String userEmail);
}
