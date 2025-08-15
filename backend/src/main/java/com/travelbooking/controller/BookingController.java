package com.travelbooking.controller;

import com.travelbooking.dto.BookingDTO;
import com.travelbooking.dto.BookingResponseDTO;
import com.travelbooking.dto.UserDTO;
import com.travelbooking.security.JwtGenerator;
import com.travelbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/booking")
@Tag(name = "User Booking Controller", description = "User booking his trip")
@PreAuthorize("hasRole('USER')")
public class BookingController {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private BookingService bookingService;

    private String extractEmailFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header != null && header.startsWith("Bearer ") ? header.substring(7) : null;
        return jwtGenerator.getUserNameFromJWT(token);
    }

    @Operation(summary = "Create a booking")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/create")
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO, HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        return bookingService.createBooking(bookingDTO, email);
    }

    @Operation(summary = "Get all logged in users booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found or no bookings found")
    })
    @GetMapping("/me")
    public List<BookingResponseDTO> getAllBooking(HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        return bookingService.getAllBookings(email);
    }

    @Operation(summary = "Get booking by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Booking does not belong to user"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public BookingResponseDTO getBooking(@PathVariable Long id, HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        return bookingService.getBooking(id, email);
    }

    @Operation(summary = "Delete booking by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User not allowed to delete this booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable Long id, HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        bookingService.deleteBookingById(id, email);
        return ResponseEntity.ok(Collections.singletonMap("message", "Booking cancelled successfully"));
    }
}
