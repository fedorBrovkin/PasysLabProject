package com.epam.labproject.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Payment))
      return false;
    if (obj == null) // not need
      return false;
    if (this.amount == ((Payment) obj).getAmount() &
            this.time.equals(((Payment) obj).getTime()) &
            this.source.equals(((Payment) obj).getSource()) &
            this.target.equals(((Payment) obj).getTarget())) {
      return true;
    }
    return false;
  }
}