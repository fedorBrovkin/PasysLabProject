package com.epam.labproject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="creditcard")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int account_id;
    private int cvc;

    private Date date_of_death;
    @ManyToOne
    @Column(name="user_id")
    private User userId;
    private int number;

    public  CreditCard(){

      }
}
