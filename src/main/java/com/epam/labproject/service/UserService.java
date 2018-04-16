package com.epam.labproject.service;

import com.epam.labproject.model.entity.Role;
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

    public void createUser(User user){
        if(user!=null) {
            if(userRepository.findByLogin(user.getLogin())==null) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                user.setRole(roleService.findByName("ROLE_USER"));
                userRepository.save(user);
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
            }else{
                System.out.println("already exist page have to be here");
            }
        }
    }
    public void save(User user){
        userRepository.save(user);
    }
    public User getUser(String login){
        return userRepository.findByLogin(login);
    }
    public User delete(String login){
        User user = userRepository.findByLogin(login);
        if(user!=null)
        userRepository.delete(user);
        return user;
    }
    public void changeRole(String login, String roleName){
        User user = this.getUser(login);
        Role role = roleService.findByName(roleName);
        if(user!=null&&role!=null){
         user.setRole(role);
         userRepository.save(user);
        }
    }
    public void changePassword(String login,String password){
        User user = getUser(login);
        String psw = bCryptPasswordEncoder.encode(password);
        if(user!=null) {
            user.setPassword(psw);
            userRepository.save(user);
        }
    }
}