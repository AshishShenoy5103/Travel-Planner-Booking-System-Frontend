package com.travelbooking.controller;

import com.travelbooking.dto.AdminViewDTO;
import com.travelbooking.dto.UserDTO;
import com.travelbooking.security.JwtGenerator;
import com.travelbooking.service.UserService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "User management of his profile")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;

    private String extractEmailFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header != null && header.startsWith("Bearer ") ? header.substring(7) : null;
        return jwtGenerator.getUserNameFromJWT(token);
    }

    @Operation(summary = "Get user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public UserDTO getCurrentUser(HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        return userService.getCurrentUserProfile(email);
    }


    @Operation(summary = "Update user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/me")
    public UserDTO updateCurrentUser(@RequestBody UserDTO dto, HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        return userService.updateCurrentUserProfile(email, dto);
    }


    @Operation(summary = "Delete user account")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Map<String, String>> deleteCurrentUser(HttpServletRequest request) {
        String email = extractEmailFromToken(request);
        userService.deleteCurrentUserProfile(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
    }
}
