package com.epam.labproject.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="payment")
public class Payment extends IdentifiableEntity{
    @ManyToOne
    private CreditCard source;//id_card
    @ManyToOne
    private CreditCard target;//id_card

    private BigDecimal amount;
    private Date time;

    public Payment(){

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
