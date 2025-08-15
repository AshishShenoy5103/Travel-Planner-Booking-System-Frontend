package com.travelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private String destination;
    private Integer rate;
    private LocalDate bookingDate;
    private Integer numberOfPeople;
    private LocalDateTime createdAt;
    private String status;
    private String userName;
    private Long userId;
}
