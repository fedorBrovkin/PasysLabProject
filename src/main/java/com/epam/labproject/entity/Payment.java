package com.epam.labproject.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "payment")
public class Payment extends AbstractIdentifiableEntity {

  @ManyToOne
  @JoinColumn(name = "source_id")
  private CreditCard source;
  @ManyToOne
  @JoinColumn(name = "target_id")
  private CreditCard target;

  @Positive
  @Max(15000)
  @Column(name = "amount")
  private BigDecimal amount;
  @Column(name = "time")
  private LocalDateTime time;

  public CreditCard getSource() {
    return source;
  }

  public void setSource(CreditCard source) {
    this.source = source;
  }

  public CreditCard getTarget() {
    return target;
  }

  public void setTarget(CreditCard target) {
    this.target = target;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }
}