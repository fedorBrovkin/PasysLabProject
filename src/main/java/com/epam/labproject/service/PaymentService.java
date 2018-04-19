package com.epam.labproject.service;

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
    
    accountService.findByNumber(payment.getSource().getAccount().getNumber())
            .getBalance().subtract(payment.getAmount());
    accountService.findByNumber(payment.getTarget().getAccount().getNumber())
            .getBalance().subtract(payment.getAmount());
    payment.setTime(LocalDateTime.now());
    save(payment);
  }

    public List<Payment> findAllBySource(CreditCard creditCard) {
      List<Payment> allBySource = paymentRepository.findAllBySource(creditCard);
      allBySource.sort(Comparator.comparing(Payment::getTime));
      return allBySource;
    }
}
