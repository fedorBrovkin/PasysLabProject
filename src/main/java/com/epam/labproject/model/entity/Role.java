package com.epam.labproject.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractIdentifiableEntity {

  @Column(name = "name",unique = true)
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
    return String.format("Customer[name='%s']", name);
  }
}
