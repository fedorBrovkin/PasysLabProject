package com.epam.labproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role extends AbstractIdentifiableEntity implements GrantedAuthority {

    @Column(name = "name", unique = true)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Role[name='%s']", name);
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
