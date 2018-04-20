package com.epam.labproject.entity;
import com.epam.labproject.form.CardForm;

import java.util.Date;
import java.time.LocalDateTime;

import javax.persistence.*;


@Entity
@Table(name = "credit_card")
public class CreditCard extends AbstractIdentifiableEntity {

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
  @Column(name = "cvc")
  private int cvc;
  @Column(name = "expiration_date")
  private LocalDateTime expirationDate;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column(name = "number",unique = true)
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDateTime expirationDate) {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof CreditCard))
      return false;
    if (obj == null) // not need
      return false;
    if (this.number == ((CreditCard)obj).getNumber() &
            this.cvc == ((CreditCard) obj).getCvc() &
            this.expirationDate.equals(((CreditCard) obj).getExpirationDate()) &
            this.account.equals(((CreditCard) obj).getAccount())) {
      return true;
    }
    return false;
  }
}