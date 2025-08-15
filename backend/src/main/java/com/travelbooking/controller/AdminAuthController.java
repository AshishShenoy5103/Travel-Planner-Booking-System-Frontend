package com.travelbooking.controller;

import com.travelbooking.dto.AdminLoginDTO;
import com.travelbooking.exception.AdminAccessDeniedException;
import com.travelbooking.exception.InvalidCredentialsException;
import com.travelbooking.security.JwtGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/admin")
@Tag(name = "Admin Auth Controller", description = "Admin login")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class AdminAuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Operation(summary = "Admin Login", description = "Authenticate admin and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins are allowed")
    })
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginDTO adminLoginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            adminLoginDTO.getEmail(),
                            adminLoginDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        try {
            System.out.println("Authorities: " + authentication.getAuthorities());
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AdminAccessDeniedException("Only admins can login here");
        }

        String token = jwtGenerator.generateToken(authentication, "ADMIN");
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
