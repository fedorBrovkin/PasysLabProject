package com.epam.labproject.service;

import com.epam.labproject.entity.User;
import java.util.List;

public interface UserService {

  User getUserByLogin(String login);

  void save(User user);

  User delete(String login);

  User update(User user);

  List<User> findAll();
}
