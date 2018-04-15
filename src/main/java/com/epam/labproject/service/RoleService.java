package com.epam.labproject.service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

//  @Autowired
//  public RoleService(RoleRepository roleRepository) {
//    this.roleRepository = roleRepository;
//  }

  public static final String DEFAULT_ROLE_NAME = "USER";

  public void save(Role role) {
    roleRepository.save(role);
  }

  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }
}
