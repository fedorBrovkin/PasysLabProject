package com.epam.labproject.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
  @OneToMany
  private List<CreditCard> cards;

  public User() {
    this.cards = new ArrayList<CreditCard>();
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

  public List<CreditCard> getCards() {
    return cards;
  }

  public void setCards(List<CreditCard> cards) {
    this.cards = cards;
  }
}