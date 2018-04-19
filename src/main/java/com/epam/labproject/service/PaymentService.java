package com.epam.labproject.service;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final AccountService accountService;

  @Autowired PaymentService(PaymentRepository paymentRepository, AccountService accountService){
    this.paymentRepository=paymentRepository;
    this.accountService = accountService;
  }

  private void save(Payment payment) { paymentRepository.save(payment); }

  /**
   * Create payment.
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public void createPayment(@Valid Payment payment) throws PasysException{
    if (payment == null) {
      return;
    }
    
    BigDecimal paymentBalance=payment.getSource().getAccount().getBalance();

    if (payment.getAmount().compareTo(paymentBalance) >= 1) {
      throw new PasysException("No funds");
    }

    Account sourceAccount=accountService.findByNumber(payment.getSource().getAccount().getNumber());
            sourceAccount.getBalance().add(payment.getAmount());
    Account targetAccount = accountService.findByNumber(payment.getTarget().getAccount().getNumber());
            targetAccount.getBalance().subtract(payment.getAmount());
    payment.setTime(LocalDateTime.now());
    accountService.save(sourceAccount);
    accountService.save(targetAccount);
    save(payment);
  }

    public List<Payment> findAllBySource(CreditCard creditCard) {
      List<Payment> allBySource = paymentRepository.findAllBySource(creditCard);
      allBySource.sort(Comparator.comparing(Payment::getTime));
      return allBySource;
    }
}
