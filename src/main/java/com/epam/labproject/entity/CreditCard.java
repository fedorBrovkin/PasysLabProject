package com.epam.labproject.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "credit_card")
public class CreditCard extends AbstractIdentifiableEntity {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "cvc")
    private int cvc;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "number", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int number;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) obj;
        if (this.number == other.getNumber() &&
                this.expirationDate != null &&
                this.expirationDate.equals(other.getExpirationDate()) &&
                this.account != null && this.account.equals(other.getAccount())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number)
                .append(expirationDate)
                .append(account)
                .toHashCode();
    }
}