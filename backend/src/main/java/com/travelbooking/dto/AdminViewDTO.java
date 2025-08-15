package com.travelbooking.dto;


import com.travelbooking.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminViewDTO {
    private Long userId;
    private String email;
    private UserType userType;
    private LocalDateTime createdAt;
    private String firstName;
    private String lastName;
    private String aadharNumber;
    private String city;
    private String phoneNumber;
}
