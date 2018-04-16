package com.epam.labproject.service;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;
import com.epam.labproject.entity.User;
import com.epam.labproject.repository.CreditCardRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

  @Autowired
  private CreditCardRepository creditCardRepository;
  @Autowired
  private PaymentService paymentService;
  @Autowired
  private UserService userService;

  /**
   * Create credit card.
   */
  public void save(CreditCard creditCard) {
    creditCardRepository.save(creditCard);
    User user = creditCard.getUser();
    user.getCards().add(creditCard);
    userService.save(user);

  }

  /**
   * Delete credit card.
   */
  public void delete(CreditCard creditCard) {
    if (creditCard != null) {
      for (CreditCard cc : creditCard.getUser().getCards()) {
        if (cc.getNumber() == creditCard.getNumber()) {
          creditCard.getUser().getCards().remove(cc);
        }
      }
      creditCardRepository.delete(creditCardRepository.findByNumber(creditCard.getNumber()));
    }
  }

  /**
   * Find credit card by number.
   */
  public CreditCard findByNumber(int number) {
    return creditCardRepository.findByNumber(number);
  }

  /**
   * Do payment.
   */
  public void doPayment(int sourceNumber, int targetNumber, double amount) {
    CreditCard source = this.findByNumber(sourceNumber);
    CreditCard target = this.findByNumber(targetNumber);
    if (source != null) {
      if (target != null) {
        Payment payment = new Payment();
        payment.setSource(source);
        payment.setTarget(target);
        payment.setAmount(new BigDecimal(amount));
        paymentService.createPayment(payment);
      } else {
        //NO SUCH TARGET CARD
      }
    } else {
      //NO SUCH SOURCE CARD
    }
  }

}