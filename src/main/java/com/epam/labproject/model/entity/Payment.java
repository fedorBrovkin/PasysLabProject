package com.epam.labproject.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment extends AbstractIdentifiableEntity {

  @ManyToOne
  @JoinColumn(name = "source_id")
  private CreditCard source;//id_card
  @ManyToOne
  @JoinColumn(name = "target_id")
  private CreditCard target;//id_card

  @Column(name = "amount")
  private BigDecimal amount;
  @Column(name = "time")
  private Date time;

  public Payment() {

  }

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

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }
}