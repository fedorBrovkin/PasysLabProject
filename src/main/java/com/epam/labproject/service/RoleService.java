package com.epam.labproject.service;


import com.epam.labproject.entity.Role;
import com.epam.labproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private RoleRepository roleRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public void save(Role role) {
    if (role != null && !role.getName().isEmpty()
        && roleRepository.findByName(role.getName()) == null) {
      roleRepository.save(role);
    }
  }

  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }

  public void delete(Role role) {
    if (role != null) {
      Role persistedRole = roleRepository.findByName(role.getName());
      if (persistedRole != null) {
        roleRepository.delete(persistedRole);
      }
    }
  }

}
