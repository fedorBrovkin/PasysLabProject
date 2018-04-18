package com.epam.labproject.form;


public class PaymentForm {
    int sourceCard;
    String targetCard;
    String amount;

    public String getAmount() {
        return amount;
    }

    public int getSourceCard() {
        return sourceCard;
    }

    public String getTargetCard() {
        return targetCard;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSourceCard(int sourceCard) {
        this.sourceCard = sourceCard;
    }

    public void setTargetCard(String targetCard) {
        this.targetCard = targetCard;
    }
}
