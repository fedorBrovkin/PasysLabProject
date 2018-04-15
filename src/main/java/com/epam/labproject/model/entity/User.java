package com.epam.labproject.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractIdentifiableEntity {

  @Column(name = "login", unique = true)
  private String login;
  @Column(name = "password")
  private String password;
  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;
  @OneToMany(mappedBy = "user",orphanRemoval = true, targetEntity = CreditCard.class)
  private List<CreditCard> cards=new  ArrayList<CreditCard>();
  public User(){
  }

  public List<CreditCard> getCards() {
    return cards;
  }
  public void setCards(List<CreditCard> cards) {
    this.cards = cards;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
            "id='" + getId() + '\'' +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", role=" + role +
            ", cards=" + cards +
            '}';
  }
}
