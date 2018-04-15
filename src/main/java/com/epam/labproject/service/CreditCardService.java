package com.epam.labproject.service;


import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.Payment;
import com.epam.labproject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditCardService {
    private CreditCardRepository creditCardRepository;
    private PaymentService paymentService;


    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository,
                             PaymentService paymentService){
        this.creditCardRepository=creditCardRepository;
        this.paymentService=paymentService;
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
    public void doPayment(int sourceNumber,int targetNumber, double amount){
        CreditCard source=this.findByNumber(sourceNumber);
        CreditCard target=this.findByNumber(targetNumber);
        if(source!=null){
            if(target!=null){
                Payment payment=new Payment();
                payment.setSource(source);
                payment.setTarget(target);
                payment.setAmount(new BigDecimal(amount));
                paymentService.createPayment(payment);
            }else{
                //NO SUCH TARGET CARD
            }
        }else{
            //NO SUCH SOURCE CARD
        }
    }
}
