package com.epam.labproject.service;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

  private CreditCardRepository creditCardRepository;

  @Autowired
  public CreditCardService(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  public void save(CreditCard creditCard) {
    creditCardRepository.save(creditCard);
  }
}
