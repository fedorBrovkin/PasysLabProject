package com.epam.labproject.form;


import com.epam.labproject.entity.CreditCard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardForm {
    int cardNumber;
    int accountNumber;
    double balance;

    public CardForm(int cardNumber, int accountNumber, double balance) {
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setAccountNumber(int accountNumber) {

        this.accountNumber = accountNumber;
    }

    public void setCardNumber(int cardNumber) {

        this.cardNumber = cardNumber;
    }

    public double getBalance() {

        return balance;
    }

    public int getCardNumber() {

        return cardNumber;
    }

    public static List<CardForm> getCardFormList(List<CreditCard> cardList) {
        List<CardForm> cards = new ArrayList<>(cardList.size());
        for (CreditCard card : cardList) {
            int accountNumber = card.getAccount().getNumber();
            int cardNumber = card.getNumber();
            BigDecimal balance = card.getAccount().getBalance();
            cards.add(new CardForm(cardNumber, accountNumber, balance.doubleValue()));
        }
        return cards;
    }
}
