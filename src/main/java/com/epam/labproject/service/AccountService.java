package com.epam.labproject.service;


import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.Payment;
import com.epam.labproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Account account){accountRepository.delete(account);}
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
}
