package com.travelbooking.controller;


import com.travelbooking.dto.AdminLoginDTO;
import com.travelbooking.dto.AdminViewDTO;
import com.travelbooking.dto.BookingResponseDTO;
import com.travelbooking.exception.InvalidCredentialsException;
import com.travelbooking.security.JwtGenerator;
import com.travelbooking.service.AdminService;
import com.travelbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Admin operations for user management")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtGenerator jwtGenerator;

    private String extractEmailFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header != null && header.startsWith("Bearer ") ? header.substring(7) : null;
        return jwtGenerator.getUserNameFromJWT(token);
    }

    @Operation(summary = "Get My details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved admin details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @GetMapping("/me")
    public AdminViewDTO getMyDetails(HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        return adminService.getMyInfo(email);
    }


    @Operation(summary = "Get All users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @GetMapping("/users")
    public List<AdminViewDTO> getAllUsers() {return adminService.getAllUser();}


    @Operation(summary = "Get All Admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of admins retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @GetMapping("/admins")
    public List<AdminViewDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }


    @Operation(summary = "Get user By ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("user/{id}")
    public AdminViewDTO getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }


    @Operation(summary = "Get user by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/email/{email}")
    public AdminViewDTO getUserByEmail(@PathVariable String email) {
        return adminService.getUserByEmail(email);
    }


    @Operation(summary = "Update user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/user/{id}")
    public AdminViewDTO updateUser(@PathVariable Long id, @RequestBody AdminViewDTO dto) {
        return adminService.updateUserById(id, dto);
    }


    @Operation(summary = "Delete user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
    }


    @Operation(summary = "Get all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of bookings"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "204", description = "No bookings found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/users/bookings")
    public List<BookingResponseDTO> getAllBookings() {
        return adminService.getAllBookings();
    }


    @Operation(summary = "Get Booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found and returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Booking not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/booking/{id}")
    public BookingResponseDTO getBookingById(@PathVariable Long id) {
        return adminService.getUserBooking(id);
    }


    @Operation(summary = "Update user booking status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Booking not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/user/booking/{id}")
    public BookingResponseDTO updateUserBooking(@PathVariable Long id, @RequestBody BookingResponseDTO bookingResponseDTO) {
        return adminService.updateUserBooking(id, bookingResponseDTO);
    }


    @Operation(summary = "Delete booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Booking not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/user/booking/{id}")
    public ResponseEntity<Map<String, String>> deleteBookingById(@PathVariable Long id) {
        adminService.deleteBookingById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Booking deleted successfully"));
    }
}
