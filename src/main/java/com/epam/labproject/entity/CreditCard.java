package com.epam.labproject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="creditcard")
public class CreditCard extends AbstractIdentifiableEntity{
    @ManyToOne
    @Column(name="account_id")
    private Account accountId;
    private int cvc;
    @Column(name="date_of_death")
    private Date dateOfDeath;
    @ManyToOne
    @Column(name="user_id")
    private User userId;
    private int number;

    public  CreditCard(){

    }
    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public Date getDate_of_death() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
