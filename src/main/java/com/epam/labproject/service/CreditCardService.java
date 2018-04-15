package com.epam.labproject.service;


import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    private CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository){
        this.creditCardRepository=creditCardRepository;
    }
    public void save(CreditCard creditCard){
        creditCardRepository.save(creditCard);
    }
    public void delete(CreditCard creditCard){
        if(creditCard!=null){
            creditCardRepository.delete(creditCardRepository.findByNumber(creditCard.getNumber()));
        }
    }
    public CreditCard findByNumber(int number){
        return creditCardRepository.findByNumber(number);
    }

}
