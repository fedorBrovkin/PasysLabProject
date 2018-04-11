package com.epam.labproject.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;



@Entity
@Table(name = "account")
public class Account extends IdentifiableEntity{
    @Column(unique = true)
    private int number;
    private BigDecimal balance;
    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;
    @Column(nullable = false)
    private boolean status=false;
    @ManyToOne
    @Column(name="user_id")
    private User userId;

    public Account(){
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
