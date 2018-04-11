package com.epam.labproject.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "account")
public class Account {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;
    @Column(unique = true)
    private int number;

    private BigDecimal balance;
    @Column()
    private LocalDate dateOfDeath;
    @Column(nullable = false)
    private boolean status=false;
    @ManyToOne
    @Column(name="user_id")
    private User userId;

    public Account(){

    }

}
