package com.epam.labproject.form;

/**
 * Created by Никита on 17.04.2018.
 */
public class PaymentForm {
    int sourceCard;
    String targetCard;
    String amount;

    public String getAmount() {
        return amount;
    }

    public String getTargetCard() {

        return targetCard;
    }

    public int getSourceCard() {

        return sourceCard;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTargetCard(String targetCard) {

        this.targetCard = targetCard;
    }

    public void setSourceCard(int sourceCard) {

        this.sourceCard = sourceCard;
    }
}
