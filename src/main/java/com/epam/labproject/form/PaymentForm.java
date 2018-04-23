package com.epam.labproject.form;


import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentForm {

  private String data;
  private double amount;
  private int source;
  private int target;

  public PaymentForm() {
  }

  public PaymentForm(String data, double amount, int source, int target) {
    this.data = data;
    this.amount = amount;
    this.source = source;
    this.target = target;
  }

  public static List<PaymentForm> getPaymentList(List<Payment> payments, CreditCard card) {
    List<PaymentForm> paymentsList = new ArrayList<>();
    for (Payment payment : payments) {
      String data = payment.getTime().toString();
      double amount;
      int source = payment.getSource().getNumber();
      int target = payment.getTarget().getNumber();
      if (isTarget(payment, card)) {
        amount = payment.getAmount().doubleValue();
      } else {
        amount = payment.getAmount().doubleValue() * (-1);
      }
      PaymentForm payListForm = new PaymentForm(data, amount, source, target);
      paymentsList.add(payListForm);
    }
    return paymentsList;
  }

  private static boolean isTarget(Payment payment, CreditCard creditCard) {
    return payment.getTarget().getNumber() == creditCard.getNumber();
  }

  public int getSource() {
    return source;
  }

  public int getTarget() {
    return target;
  }

  public String getData() {
    return data;

  }

  public double getAmount() {

    return amount;
  }

  public void setData(String data) {
    this.data = data;
  }

  public void setTarget(int target) {
    this.target = target;
  }

  public void setSource(int source) {

    this.source = source;
  }

  public void setAmount(double amount) {

    this.amount = amount;
  }
}
