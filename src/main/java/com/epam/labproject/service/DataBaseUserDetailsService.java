package com.epam.labproject.service;

import com.epam.labproject.entity.User;
import com.epam.labproject.repository.UserRepository;

import java.util.Collections;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DataBaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user by username.
     */
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByLogin(username);
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), Collections.singleton(user.getRole()));
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}