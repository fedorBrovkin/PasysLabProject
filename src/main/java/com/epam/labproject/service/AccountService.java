package com.epam.labproject.service;

import com.epam.labproject.entity.Account;
import com.epam.labproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  public void save(Account account) {
    accountRepository.save(account);
  }

  public void delete(Account account) {
    accountRepository.delete(account);
  }

  public Account findByNumber(int number) {
    return accountRepository.findByNumber(number);
  }

}
