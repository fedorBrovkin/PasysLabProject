package com.epam.labproject.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  @OneToMany(mappedBy = "user", targetEntity = CreditCard.class, fetch = FetchType.EAGER)
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
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User) obj;
    if (this.login != null && this.login.equals(other.getLogin())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
            .append(login)
            .toHashCode();
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
