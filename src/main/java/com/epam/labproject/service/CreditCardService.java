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
    private AccountService accountService;
    private PaymentService paymentService;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository, AccountService accountService,
                             PaymentService paymentService){
        this.creditCardRepository=creditCardRepository;
        this.accountService=accountService;
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
    public boolean makeTransfer(CreditCard source,CreditCard target,double amount){
        if(source!=null&&target!=null){
            Payment payment=accountService.makeTransfer(source.getAccount(),target.getAccount(),new BigDecimal(amount));
            if(payment!=null){
                payment.setSource(source);
                payment.setTarget(target);
                paymentService.save(payment);
            }
            //throw some Exception
            return false;
        }
        return false;
    }

}
