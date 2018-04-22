package com.epam.labproject.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unblock_request")
public class UnblockRequest extends AbstractIdentifiableEntity {

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;
  @OneToOne
  @JoinColumn(name = "account_id")
  Account account;
  @Column(name = "time")
  private LocalDateTime time;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Account getAccount() {

    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }
}
