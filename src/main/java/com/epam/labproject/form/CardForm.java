package com.epam.labproject.form;


import com.epam.labproject.entity.CreditCard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardForm {
    private int cardNumber;
    private int accountNumber;
    private double balance;
    private String currentCondition;

    public CardForm() {
    }

    public CardForm(int cardNumber, int accountNumber, double balance) {
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
        currentCondition = "Card: " + Integer.toString(cardNumber) + " account:" + Integer.toString(accountNumber) + " balance:" + Double.toString(balance);
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

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }


}
