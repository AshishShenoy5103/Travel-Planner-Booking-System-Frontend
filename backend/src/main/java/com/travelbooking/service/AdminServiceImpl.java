package com.travelbooking.service;

import com.travelbooking.dto.AdminViewDTO;
import com.travelbooking.dto.BookingResponseDTO;
import com.travelbooking.exception.BookingIdNotFoundException;
import com.travelbooking.exception.UserNotFoundException;
import com.travelbooking.model.Booking;
import com.travelbooking.model.Profile;
import com.travelbooking.model.User;
import com.travelbooking.model.UserType;
import com.travelbooking.repository.BookingRepository;
import com.travelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private AdminViewDTO mapToAdminViewDTO(User user) {
        Profile profile = user.getProfile();

        return new AdminViewDTO(
                user.getUserId(),
                user.getEmail(),
                user.getUserType(),
                user.getCreatedAt(),
                profile != null ? profile.getFirstName() : null,
                profile != null ? profile.getLastName() : null,
                profile != null ? profile.getAadharNumber() : null,
                profile != null ? profile.getCity() : null,
                profile != null ? profile.getPhoneNumber() :  null
        );
    }

    @Override
    public AdminViewDTO getMyInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        return mapToAdminViewDTO(user);
    }


    @Override
    public List<AdminViewDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().filter(user -> user.getUserType() == UserType.USER).map(this::mapToAdminViewDTO).collect(Collectors.toList());
    }

    @Override
    public List<AdminViewDTO> getAllAdmins() {
        List<User> users = userRepository.findAll();
        return users.stream().filter(user -> user.getUserType() == UserType.ADMIN).map(this::mapToAdminViewDTO).collect(Collectors.toList());
    }

    @Override
    public AdminViewDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found With ID " + id));
        return mapToAdminViewDTO(user);
    }

    @Override
    public AdminViewDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found With Email " + email));
        return mapToAdminViewDTO(user);
    }

    @Override
    public AdminViewDTO updateUserById(Long id, AdminViewDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found With ID " + id));

        user.setEmail(dto.getEmail());
        user.setUserType(dto.getUserType());

        Profile profile = user.getProfile();
        if(profile != null) {
            profile.setFirstName(dto.getFirstName());
            profile.setLastName(dto.getLastName());
            profile.setAadharNumber(dto.getAadharNumber());
            profile.setCity(dto.getCity());
            profile.setPhoneNumber(dto.getPhoneNumber());
        }
        User updated =userRepository.save(user);
        return mapToAdminViewDTO(updated);
    }

    @Override
    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToBookingResponseDTO)
                .collect(Collectors.toList());
    }

    private BookingResponseDTO mapToBookingResponseDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setDestination(booking.getDestination());
        dto.setRate(booking.getRate());
        dto.setBookingDate(booking.getBookingDate());
        dto.setNumberOfPeople(booking.getNumberOfPeople());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setStatus(booking.getStatus());
        dto.setUserName(booking.getUser().getProfile().getFirstName());
        dto.setUserId(booking.getUser().getUserId());
        return dto;
    }

    @Override
    public BookingResponseDTO updateUserBooking(Long id, BookingResponseDTO bookingResponseDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with id: " + id));

        booking.setDestination(bookingResponseDTO.getDestination());
        booking.setRate(bookingResponseDTO.getRate());
        booking.setBookingDate(bookingResponseDTO.getBookingDate());
        booking.setNumberOfPeople(bookingResponseDTO.getNumberOfPeople());
        booking.setStatus(bookingResponseDTO.getStatus());

        Booking updatedBooking = bookingRepository.save(booking);

        return mapToBookingResponseDTO(updatedBooking);
    }

    @Override
    public BookingResponseDTO getUserBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with id: " + id));

        return mapToBookingResponseDTO(booking);
    }

    @Override
    public void deleteBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with id: " + id));

        bookingRepository.delete(booking);
    }
}
