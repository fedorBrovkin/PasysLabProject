package com.epam.labproject.service;


import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.Payment;
import com.epam.labproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    public void save(Account account){
        accountRepository.save(account);
    }
    public void close(Account account){}
    public void update(Account account){
        if(account!=null){
            Account currentAccount=accountRepository.findByNumber(account.getNumber());
            if(currentAccount!=null){
                currentAccount.setBalance(account.getBalance());
                currentAccount.setBirthDay(account.getBirthDay());
                currentAccount.setStatus(account.isStatus());
                currentAccount.setUser(account.getUser());
            }
        }
    }
    public Account findByNumber(int number){
        return accountRepository.findByNumber(number);
    }
    public boolean withdraw(Account source, Account target, BigDecimal amount){
            if (amount.compareTo(source.getBalance()) < 1) {
                source.setBalance(source.getBalance().divide(amount));
                target.setBalance(target.getBalance().add(amount));
                this.save(source);
                this.save(target);
                return true;
            }
        return false;
    }
    public Payment makeTransfer(Account source, Account target, BigDecimal amount){
        if(source!=null&&target!=null){
            if(withdraw(source,target,amount)){
                Payment payment =new Payment();
                payment.setAmount(amount);
                payment.setTime(LocalDateTime.now());
                return payment;
            }
            //throw NoFundsException
            return null;
        }
        return null;
    }

}
