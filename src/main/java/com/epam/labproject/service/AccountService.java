package com.epam.labproject.service;


import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.repository.AccountRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private AccountRepository accountRepository;
  private UserService userService;
  private UnblockRequestService requestService;

  @Autowired
  public AccountService(AccountRepository accountRepository, UserService userService,
      UnblockRequestService requestService) {
    this.accountRepository = accountRepository;
    this.userService = userService;
  }

  public void save(Account account) {
    accountRepository.save(account);
  }

  public void delete(Account account) {
    accountRepository.delete(account);
  }

  public Account findByNumber(int number) {
    return accountRepository.findByNumber(number);
  }

  /**
   * RETURN LIST OF Active ACCOUNTS for current User
   */
  public List<Account> findAllByUserNameAndStatusTrue(String login) {
    return accountRepository
        .findAllByUserAndStatusIsTrue(userService.getUser(login));
  }

  /**
   * RETURN LIST OF Blocked ACCOUNTS for current User
   */
  public List<Account> findAllByUserByUserNameAndStatusFalse(String login) {
    return accountRepository
        .findAllByUserAndStatusIsFalse(userService.getUser(login));
  }

  public void createAccount(String login) {
    User user = userService.getUser(login);
    Account account = new Account();
    account.setBalance(new BigDecimal(1000));
    account.setStatus(true);
    account.setUser(user);
    account.setDateOfCreation(LocalDateTime.now());
    account.setNumber(accountNumberBuilder());
    this.save(account);
  }

  public List<Account> findAllByUser(User user) {
    return accountRepository.findAllByUser(user);
  }

    /**
     * Admin fixed transaction to target card.
     *
     * @param accountNumber target card number.
     */
    public void giveMoney(int accountNumber) throws PasysException{
        Account account=accountRepository.findByNumber(accountNumber);
        if(account!=null&&account.isStatus()){
            BigDecimal balance=account.getBalance();
            account.setBalance(balance.add(new BigDecimal(10000)));
            accountRepository.save(account);
        }else{
          throw new PasysException("?accountBlocked");
        }
    }

  /**
   * Changing account status for currentAccount
   */
  public void changeStatus(int accountNumber) {
    Account a = accountRepository.findByNumber(accountNumber);
    a.setStatus(!a.isStatus());
    this.save(a);
  }

  /**
   * Blocking account
   */
  public void blockAccount(int accountNumber) {
    Account a = accountRepository.findByNumber(accountNumber);
    a.setStatus(false);
    this.save(a);
  }

  /**
   * True if account is not blocled
   */
  public boolean isActive(int accountNumber) {
    return accountRepository.findByNumber(accountNumber).isStatus();
  }

  /**
   * Get current account balance
   */
  public double getBalance(int accountNumber) {
    Account account = accountRepository.findByNumber(accountNumber);
    if (account != null) {
      return account.getBalance().doubleValue();
    }
    return 0.0;
  }

  /**
   * Random number creator for new AccountCreation
   */
  private int accountNumberBuilder() {
    int number = 0;
    do {
      number = 100000 + (((int) (Math.random() * 100000)) % 900000);
    } while (accountRepository.findByNumber(number) != null);
    return number;
  }
}