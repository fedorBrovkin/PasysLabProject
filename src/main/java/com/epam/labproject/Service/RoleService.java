package com.epam.labproject.Service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    public void save(Role role){
        roleRepository.save(role);
    }
}