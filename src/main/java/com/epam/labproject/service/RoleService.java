package com.epam.labproject.service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  public static final String DEFAULT_ROLE_NAME = "USER";

  @Autowired
  private RoleRepository roleRepository;

  /**
   * Save role.
   */
  public void save(Role role) {
    if (role != null && !role.getName().isEmpty()) {
      roleRepository.save(role);
    }
  }

  /**
   * Find role by name.
   */
  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }

  /**
   * Delete role.
   */
  public void delete(Role role) {
    if (role != null) {
      if (roleRepository.findByName(role.getName()) != null) {
        roleRepository.delete(roleRepository.findByName(role.getName()));
      }
    }
  }
}
