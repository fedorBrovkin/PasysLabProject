package com.epam.labproject.service;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;
import com.epam.labproject.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  public void save(Payment payment) {
    paymentRepository.save(payment);
  }

  /**
   * Create payment.
   */
  public void createPayment(@Valid Payment payment) {
    if (makeTransfer(payment)) {
      save(payment);
    } else {
      //do smthing
    }
  }

  public List<Payment> findAllBySource(CreditCard creditCard) {
    return paymentRepository.findAllBySource(creditCard);
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ)
  protected boolean makeTransfer(Payment payment) {
    if (payment != null) {
      if (payment.getAmount().compareTo(payment.getSource().getAccount().getBalance()) < 1) {
        payment.getSource().getAccount()
                .setBalance(payment.getSource().getAccount().getBalance()
                        .subtract(payment.getAmount()));
        payment.getTarget().getAccount()
            .setBalance(payment.getTarget().getAccount().getBalance()
                    .add(payment.getAmount()));
        payment.setTime(LocalDateTime.now());
        return true;
      }
    }
    return false;
  }
}
