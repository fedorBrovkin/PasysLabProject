package com.epam.labproject.form;


import com.epam.labproject.entity.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountForm {
    int accNumber;
    double balance;

    public AccountForm(int accNumber, double balance) {
        this.accNumber = accNumber;
        this.balance = balance;
    }

    public int getAccNumber() {
        return accNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccNumber(int accNumber) {
        this.accNumber = accNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static List<AccountForm> getAccountFormList(List<Account> accountList) {
        List<AccountForm> accounts = new ArrayList<>(accountList.size());
        for (Account account : accountList) {
            int accountNumber = account.getNumber();
            BigDecimal balance = account.getBalance();
            accounts.add(new AccountForm(accountNumber, balance.doubleValue()));
        }
        return accounts;
    }
}
