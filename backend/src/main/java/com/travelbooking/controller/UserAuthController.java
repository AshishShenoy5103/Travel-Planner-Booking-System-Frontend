package com.travelbooking.controller;

import com.travelbooking.dto.UserLoginDTO;
import com.travelbooking.dto.UserRegisterDTO;
import com.travelbooking.exception.AdminAccessDeniedException;
import com.travelbooking.exception.InvalidCredentialsException;
import com.travelbooking.security.JwtGenerator;
import com.travelbooking.service.UserAuthServiceImpl;
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
@RequestMapping("/api/auth/user")
@Tag(name = "User Auth Controller", description = "User Login and Register")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class UserAuthController {
    @Autowired
    private UserAuthServiceImpl userAuthServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user with email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        userAuthServiceImpl.registerUser(userRegisterDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "403", description = "Access denied - only users are allowed")
    })
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getEmail(),
                            userLoginDTO.getPassword()
                    )
            );

            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

            if (!isUser) {
                throw new AdminAccessDeniedException("Only users can login here");
            }

            System.out.println("Authorities: " + authentication.getAuthorities());

            String token = jwtGenerator.generateToken(authentication, "USER");
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return new ResponseEntity<>(response , HttpStatus.OK);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }
}
