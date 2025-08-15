package com.travelbooking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String destination;

    private Integer rate;

    private LocalDate bookingDate;

    private Integer numberOfPeople;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String status = "PENDING"; // Default status

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
