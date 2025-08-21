package com.travelbooking.service;

import com.travelbooking.dto.BookingDTO;
import com.travelbooking.dto.BookingResponseDTO;
import com.travelbooking.exception.BookingIdNotFoundException;
import com.travelbooking.exception.UnauthorizedAccessException;
import com.travelbooking.exception.UserNotFoundException;
import com.travelbooking.model.Booking;
import com.travelbooking.model.User;
import com.travelbooking.repository.BookingRepository;
import com.travelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    private BookingDTO mapToDto(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setDestination(booking.getDestination());
        dto.setRate(booking.getRate());
        dto.setBookingDate(booking.getBookingDate());
        dto.setNumberOfPeople(booking.getNumberOfPeople());
        return dto;
    }

    private Booking mapToEntity(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setDestination(dto.getDestination());
        booking.setRate(dto.getRate());
        booking.setBookingDate(dto.getBookingDate());
        booking.setNumberOfPeople(dto.getNumberOfPeople());
        return booking;
    }

    private BookingResponseDTO mapToBookingResponseDto(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setDestination(booking.getDestination());
        dto.setRate(booking.getRate());
        dto.setBookingDate(booking.getBookingDate());
        dto.setNumberOfPeople(booking.getNumberOfPeople());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDto, String email) {
        Booking booking = mapToEntity(bookingDto);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        booking.setUser(user);
        user.getBookings().add(booking);


        Booking savedBooking = bookingRepository.save(booking);
        return mapToDto(savedBooking);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        List<Booking> bookings = user.getBookings();

        return bookings.stream()
                .map(this::mapToBookingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO getBooking(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with ID: " + id));

        if (!booking.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedAccessException("Booking does not belong to the authenticated user");
        }

        return mapToBookingResponseDto(booking);
    }


    @Override
    public BookingResponseDTO updateBookingStatus(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found or not authorized"));

        if (!booking.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedAccessException("Booking does not belong to the authenticated user");
        }


        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Booking is already cancelled and cannot be changed");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        return mapToBookingResponseDto(booking);
    }


    @Override
    public void deleteBookingById(Long id, String email) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with id: " + id));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        if (!booking.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedAccessException("You are not authorized to delete this booking");
        }

        bookingRepository.delete(booking);
    }
}
