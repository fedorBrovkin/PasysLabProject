package com.epam.labproject.service;

import com.epam.labproject.entity.User;
import com.epam.labproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;
  private RoleService roleService;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManagerBuilder auth;

  /**
   * Constructor.
   */
  @Autowired
  public UserService(UserRepository userRepository, RoleService roleService,
      PasswordEncoder passwordEncoder, AuthenticationManagerBuilder auth) {

    this.userRepository = userRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
    this.auth = auth;
  }

  /**
   * Create user.
   */
  public void save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(roleService.findByName("USER"));
    userRepository.save(user);
    PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  /**
   * Delete user by login.
   */
  public User delete(String login) {
    User user = userRepository.findByLogin(login);
    userRepository.delete(user);
    return user;
  }

  /**
   * Update user.
   */
  public User update(User user) {
    if (userRepository.findByLogin(user.getLogin()) == null) {
      this.save(user);
      return null;
    }
    return null;
  }
}