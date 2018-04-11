package com.epam.entity;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Table(name="creditcard")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int account_id;
    private int cvc;

    private Date date_of_death;
    @ManyToOne
    private User user_id;
    private int number;

    public  CreditCard(){

      }
}
