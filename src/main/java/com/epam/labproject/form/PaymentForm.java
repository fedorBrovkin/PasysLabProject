package com.epam.labproject.form;


public class PaymentForm {

  private int sourceCard;
  private String targetCard;
  private String amount;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public int getSourceCard() {
    return sourceCard;
  }

  public void setSourceCard(int sourceCard) {
    this.sourceCard = sourceCard;
  }

  public String getTargetCard() {
    return targetCard;
  }

  public void setTargetCard(String targetCard) {
    this.targetCard = targetCard;
  }
}
