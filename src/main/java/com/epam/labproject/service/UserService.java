package com.epam.labproject.service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.entity.User;
import com.epam.labproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleService roleService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Create user.
   */
  public void createUser(User user) {
    if (user != null) {
      if (userRepository.findByLogin(user.getLogin()) == null) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName(RoleService.DEFAULT_ROLE_NAME));
        userRepository.save(user);
        PasswordEncoderFactories.createDelegatingPasswordEncoder();
      } else {
        System.out.println("already exist page have to be here");
      }
    }
  }

  /**
   * Save user.
   */
  public void save(User user) {
    userRepository.save(user);
  }

  /**
   * Get user by login.
   */
  public User getUser(String login) {
    return userRepository.findByLogin(login);
  }

  /**
   * Delete user by login.
   */
  public User delete(String login) {
    User user = userRepository.findByLogin(login);
    if (user != null) {
      userRepository.delete(user);
    }
    return user;
  }

  /**
   * Change user role.
   */
  public void changeRole(String login, String roleName) {
    User user = this.getUser(login);
    Role role = roleService.findByName(roleName);
    if (user != null && role != null) {
      user.setRole(role);
      userRepository.save(user);
    }
  }

  /**
   * Change user password.
   */
  public void changePassword(String login, String password) {
    User user = getUser(login);
    String psw = passwordEncoder.encode(password);
    if (user != null) {
      user.setPassword(psw);
      userRepository.save(user);
    }
  }
}