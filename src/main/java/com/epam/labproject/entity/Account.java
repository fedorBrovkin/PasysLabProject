package com.epam.labproject.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "account")
public class Account extends AbstractIdentifiableEntity {

  @Column(name = "number", unique = true)
  private int number;
  @Column(name = "balance")
  private BigDecimal balance;
  @Column(name = "birthday")
  private LocalDateTime dateOfCreation;
  @Column(name = "status", nullable = false)
  private boolean status;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public LocalDateTime getDateOfCreation() {
    return dateOfCreation;
  }
  public void setDateOfCreation(LocalDateTime dateOfCreation) {
    this.dateOfCreation = dateOfCreation;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format("Account[lastName='%s']", dateOfCreation);
  }
}
