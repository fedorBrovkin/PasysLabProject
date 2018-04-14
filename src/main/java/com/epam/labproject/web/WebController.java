package com.epam.labproject.web;

import com.epam.labproject.entity.Role;
import com.epam.labproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {


  @Autowired
  RoleRepository roleRepository;

  /**
   * This is test method.
   */
  @RequestMapping("/findall")
  public String findAll() {
    StringBuilder result = new StringBuilder();
    for (Role role : roleRepository.findAll()) {
      result.append(role.toString());
      result.append("<br>");
    }
    return result.toString();
  }
}