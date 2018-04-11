package com.epam.labproject.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private CreditCard source;//id_card
    @ManyToOne
    private CreditCard target;//id_card

    private BigDecimal amount;
    private Date time;

    public Payment(){

    }
}
