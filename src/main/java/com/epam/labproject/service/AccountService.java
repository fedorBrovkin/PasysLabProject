package com.epam.labproject.service;


import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.Payment;
import com.epam.labproject.model.entity.User;
import com.epam.labproject.repository.AccountRepository;
import com.epam.labproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private UserService userService;
    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService){
        this.accountRepository=accountRepository;
        this.userService=userService;
    }

    public void save(Account account){
        accountRepository.save(account);
    }
    public void delete(Account account){accountRepository.delete(account);}
    public Account findByNumber(int number){
        return accountRepository.findByNumber(number);
    }

    public void createAccount(String login){
     User user = userService.getUser(login);
     Account account= new Account();
     account.setBalance(new BigDecimal(1000));
     account.setStatus(true);
     account.setUser(user);
     account.setDateOfCreation(LocalDateTime.now());
     account.setNumber(accountNumberBuilder());
     this.save(account);
    }

    public void makeActive(int accountNumber){
        Account account = this.findByNumber(accountNumber);
        if(account!=null){
            account.setStatus(true);
            this.save(account);
        }
    }
    public void makeNotActive(int accountNumber){
        Account account = this.findByNumber(accountNumber);
        if(account!=null){
            account.setStatus(false);
            this.save(account);
        }
    }
    //public void
    public double getBalance(Account account){
        if(account!=null)
            return account.getBalance().doubleValue();
        return 0.0;
    }
    private int accountNumberBuilder(){
        int number=0;
        do {
            number=100000 + (((int) (Math.random() * 100000)) % 900000);
        }while(accountRepository.findByNumber(number)!=null);
        return number;
    }
}
