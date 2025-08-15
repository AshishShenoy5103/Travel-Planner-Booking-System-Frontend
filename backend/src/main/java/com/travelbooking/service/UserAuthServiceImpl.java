package com.travelbooking.service;

import com.travelbooking.dto.UserLoginDTO;
import com.travelbooking.dto.UserRegisterDTO;
import com.travelbooking.exception.InvalidCredentialsException;
import com.travelbooking.exception.UserNotFoundException;
import com.travelbooking.model.Profile;
import com.travelbooking.model.User;
import com.travelbooking.model.UserType;
import com.travelbooking.repository.ProfileRepository;
import com.travelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean loginUser(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found!"));

        if(!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Incorrect Password!");
        }
        return true;
    }

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        User newUser = new User();
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(newUser);

        Profile profile = new Profile();
        profile.setUser(newUser);
        profile.setFirstName(userRegisterDTO.getFirstName());
        profile.setLastName(userRegisterDTO.getLastName());
        profile.setAadharNumber(userRegisterDTO.getAadharNumber());
        profile.setCity(userRegisterDTO.getCity());
        profile.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        profileRepository.save(profile);
    }
}

