package com.epam.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

@Entity
//@Table(name="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private CreditCard source;//id_card
    @ManyToOne
    private CreditCard target;//id_card

    private BigDecimal amount;
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date time;

    public Payment(){

    }
}
