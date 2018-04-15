package com.epam.labproject.service;

import com.epam.labproject.entity.User;
import com.epam.labproject.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleService roleService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Find by login.
   */
  public User getUserByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  /**
   * Create user.
   */
  public void save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(roleService.findByName(RoleService.DEFAULT_ROLE_NAME));
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

  /**
   * Get all users.
   */
  public List<User> findAll() {
    List<User> users = userRepository.findAll();
    return users;
  }
}