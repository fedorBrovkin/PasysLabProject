package com.epam.labproject.service;

import com.epam.labproject.entity.Account;
import com.epam.labproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

//  @Autowired
//  public AccountService(AccountRepository accountRepository) {
//    this.accountRepository = accountRepository;
//  }

  public void save(Account account) {
    accountRepository.save(account);
  }

}
