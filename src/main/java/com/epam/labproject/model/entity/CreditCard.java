package com.epam.labproject.model.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "credit_card")
public class CreditCard extends AbstractIdentifiableEntity {

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
  @Column(name = "cvc")
  private int cvc;
  @Column(name = "expiration_date")
  private Date expirationDate;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column(name = "number",unique = true)
  private int number;

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public int getCvc() {
    return cvc;
  }

  public void setCvc(int cvc) {
    this.cvc = cvc;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public User getUser() { return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}