package com.epam.labproject.service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    public void createUser(User user) throws PasysException {
        if (user != null) {
            if (userRepository.findByLogin(user.getLogin()) == null) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                user.setRole(roleService.findByName("ROLE_USER"));
                userRepository.save(user);
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
            } else {
                throw new PasysException("?error" + PasysException.USER_ALREADY_EXIST);
            }
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getUser(String login) {
        return userRepository.findByLogin(login);
    }

    public void delete(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public void changeRole(String login, String roleName) throws PasysException {
        User user = this.getUser(login);
        Role role = roleService.findByName(roleName);
        if (user != null && role != null) {
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new PasysException("?error=" + PasysException.NO_SUCH_ROLE);
        }
    }

    public void changePassword(String login, String password) {
        User user = getUser(login);
        String psw = bCryptPasswordEncoder.encode(password);
        if (user != null) {
            user.setPassword(psw);
            userRepository.save(user);
        }
    }

}