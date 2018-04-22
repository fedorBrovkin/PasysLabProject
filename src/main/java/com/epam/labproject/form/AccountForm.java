package com.epam.labproject.form;


import com.epam.labproject.entity.Account;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class AccountForm {

  private int accNumber;
  private double balance;
  private String status;
  private String currentCondition;

  public AccountForm() {
  }

  public AccountForm(int accNumber, double balance, boolean status) {
    this.accNumber = accNumber;
    this.balance = balance;
    this.status = (status ? "Blocked" : "Active");
    currentCondition =
        Integer.toString(accNumber) + "    Balance:" + Double.toString(balance) + " Status: "
            + this.status;

  }

  public static List<AccountForm> getAccountFormList(List<Account> accountList) {
    List<AccountForm> accounts = new LinkedList<>();
    for (Account account : accountList) {
      int accountNumber = account.getNumber();
      BigDecimal balance = account.getBalance();
      accounts.add(new AccountForm(accountNumber, balance.doubleValue(), !account.isStatus()));
    }
    return accounts;
  }

  public int getAccNumber() {
    return accNumber;
  }

  public void setAccNumber(int accNumber) {
    this.accNumber = accNumber;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public String getCurrentCondition() {

    return currentCondition;
  }

  public void setCurrentCondition(String currentCondition) {
    this.currentCondition = currentCondition;
  }

  public String getStatus() {

    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
