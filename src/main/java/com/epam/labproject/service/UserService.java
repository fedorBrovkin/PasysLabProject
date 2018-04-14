package com.epam.labproject.service;

import com.epam.labproject.model.entity.User;
import com.epam.labproject.repository.RoleRepository;
import com.epam.labproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManagerBuilder auth;

    @Autowired
    public UserService(UserRepository userRepository,RoleService roleService,
                       PasswordEncoder passwordEncoder, AuthenticationManagerBuilder auth){

        this.userRepository=userRepository;
        this.roleService=roleService;
        this.bCryptPasswordEncoder=passwordEncoder;
        this.auth=auth;
    }

    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("USER"));
        userRepository.save(user);
        PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    public User delete(String login){
        User user = userRepository.findByLogin(login);
        userRepository.delete(user);
        return user;
    }

    public User update(User user){
        if(userRepository.findByLogin(user.getLogin())==null){
            this.save(user);
            return null;
        }
        //userRepository.
        return null;
    }
}