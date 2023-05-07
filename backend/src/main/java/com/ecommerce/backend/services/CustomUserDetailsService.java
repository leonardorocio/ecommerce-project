package com.ecommerce.backend.services;

import com.ecommerce.backend.configuration.CustomUserDetails;
import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User not found"));
        return new CustomUserDetails(user.getUserId(), user.getEmail(), user.getPassword(), Collections.EMPTY_LIST);
    }
}
