package com.epam.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private int number;

    private BigDecimal balance;
    @Column()
    private LocalDate dateOfDeath;
    @Column(nullable = false)
    private boolean status=false;
    @ManyToOne
    private User user_id;

    public Account(){

    }

}
