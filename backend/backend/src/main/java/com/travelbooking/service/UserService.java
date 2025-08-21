package com.travelbooking.service;

import com.travelbooking.dto.UserDTO;

/*
    UserService interface where it fetches user based on the fields in JWT token
*/

public interface UserService {
    UserDTO getCurrentUserProfile(String email);
    UserDTO updateCurrentUserProfile(String email, UserDTO dto);
    void deleteCurrentUserProfile(String email);
}
