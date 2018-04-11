package com.epam.labproject.entity;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role extends AbstractIdentifiableEntity{
    private String name;

    public Role(){

    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
