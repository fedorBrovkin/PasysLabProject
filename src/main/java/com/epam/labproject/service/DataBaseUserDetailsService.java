package com.epam.labproject.service;

import com.epam.labproject.model.entity.User;
import com.epam.labproject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class DataBaseUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    DataBaseUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByLogin(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(),grantedAuthorities);
    }
}
