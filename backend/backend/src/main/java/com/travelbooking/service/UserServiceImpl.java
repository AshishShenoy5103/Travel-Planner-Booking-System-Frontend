package com.travelbooking.service;

import com.travelbooking.dto.UserDTO;
import com.travelbooking.exception.UserNotFoundException;
import com.travelbooking.model.Profile;
import com.travelbooking.model.User;
import com.travelbooking.repository.ProfileRepository;
import com.travelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;


    private UserDTO mapToUserDTO(User user) {
        Profile profile = user.getProfile();

        return new UserDTO(
                user.getEmail(),
                profile != null ? profile.getFirstName() : null,
                profile != null ? profile.getLastName() : null,
                profile != null ? profile.getAadharNumber() : null,
                profile != null ? profile.getCity() : null,
                profile != null ? profile.getPhoneNumber() : null
        );
    }

    @Override
    public UserDTO getCurrentUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));
        return mapToUserDTO(user);
    }

    @Override
    public UserDTO updateCurrentUserProfile(String email, UserDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));

        // optional: user.setEmail(dto.getEmail());

        Profile profile = user.getProfile();
        if (profile != null) {
            profile.setFirstName(dto.getFirstName());
            profile.setLastName(dto.getLastName());
            profile.setAadharNumber(dto.getAadharNumber());
            profile.setCity(dto.getCity());
            profile.setPhoneNumber(dto.getPhoneNumber());
        }

        User updated = userRepository.save(user);
        return mapToUserDTO(updated);
    }

    @Override
    public void deleteCurrentUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));
        userRepository.delete(user);
    }
}
