package com.epam.labproject.repository;

import com.epam.labproject.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    public Role findByName(String name);
}
