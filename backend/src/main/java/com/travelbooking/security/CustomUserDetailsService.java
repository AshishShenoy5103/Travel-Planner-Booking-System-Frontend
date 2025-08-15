package com.travelbooking.security;

import com.travelbooking.exception.UserNotFoundException;
import com.travelbooking.model.User;
import com.travelbooking.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    UserDetailsService is an interface which has loadUserByUsername method.
    We are overriding this loadUserByUsername(String username) and we are returning
    UserDetails User(email, password, ROLE_getUserType());
*/

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername called with email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found!"));

        /*
            public User(String username, String password, Collection<? extends GrantedAuthority> authorities)
        */

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType().toString()))
        );
    }
}
