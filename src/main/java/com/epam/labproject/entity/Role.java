package com.epam.labproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractIdentifiableEntity {

  @Column(name = "name")
  private String name;

  public Role() {

  }

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
}
