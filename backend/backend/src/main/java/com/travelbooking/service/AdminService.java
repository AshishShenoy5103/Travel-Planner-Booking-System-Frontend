package com.travelbooking.service;

import com.travelbooking.dto.AdminViewDTO;
import com.travelbooking.dto.BookingResponseDTO;

import java.util.List;

public interface AdminService {
    AdminViewDTO getMyInfo(String email);
    List<AdminViewDTO> getAllUser();
    List<AdminViewDTO> getAllAdmins();
    AdminViewDTO getUserById(Long id);
    AdminViewDTO getUserByEmail(String email);
    AdminViewDTO updateUserById(Long id, AdminViewDTO dto);
    void deleteUserById(Long id);
    List<BookingResponseDTO> getAllBookings();
    BookingResponseDTO updateUserBooking(Long id, BookingResponseDTO bookingResponseDTO);
    BookingResponseDTO getUserBooking(Long id);
    void deleteBookingById(Long id);
}
