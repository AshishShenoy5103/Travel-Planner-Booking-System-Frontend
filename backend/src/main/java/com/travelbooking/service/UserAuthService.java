package com.travelbooking.service;

import com.travelbooking.dto.UserLoginDTO;
import com.travelbooking.dto.UserRegisterDTO;

public interface UserAuthService {
    boolean loginUser(UserLoginDTO userLoginDTO);
    void registerUser(UserRegisterDTO userRegisterDTO);
}
