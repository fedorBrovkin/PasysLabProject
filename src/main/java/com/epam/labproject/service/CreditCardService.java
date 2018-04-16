package com.epam.labproject.service;


import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.Payment;
import com.epam.labproject.model.entity.User;
import com.epam.labproject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditCardService {
    private CreditCardRepository creditCardRepository;
    private PaymentService paymentService;
    private UserService userService;
    private AccountService accountService;


    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository,
                             PaymentService paymentService,UserService userService
    ,AccountService accountService){
        this.creditCardRepository=creditCardRepository;
        this.paymentService=paymentService;
        this.userService=userService;
        this.accountService=accountService;
    }
    public void save(CreditCard creditCard){
        creditCardRepository.save(creditCard);
        User user=creditCard.getUser();
        user.getCards().add(creditCard);
        userService.save(user);

    }
    public void delete(CreditCard creditCard){
        if(creditCard!=null){
            for(CreditCard cc : creditCard.getUser().getCards()){
                if(cc.getNumber()==creditCard.getNumber())
                    creditCard.getUser().getCards().remove(cc);
            }
            creditCardRepository.delete(creditCardRepository.findByNumber(creditCard.getNumber()));
        }
    }
    public CreditCard findByNumber(int number){
        return creditCardRepository.findByNumber(number);
    }

    public void createCard(String login,int accountNumber){
            User user = userService.getUser(login);
            Account account = accountService.findByNumber(accountNumber);
            if(user!=null){
                if(account!=null){
                    CreditCard creditCard=new CreditCard();
                    creditCard.setAccount(account);
                    creditCard.setUser(user);
                    creditCard.setCvc(cvcBuider());
                    creditCard.setExpirationDate(LocalDateTime.now().plusYears(3));
                    creditCard.setNumber(this.cardNumberBuilder());
                    creditCardRepository.save(creditCard);
                }else{
                    //account not found
                }
            }else{
                //usernotfound
            }

    }

    private int cvcBuider(){
        return 100+(((int)(Math.random()*100))%900);
    }

    private int cardNumberBuilder(){
        int number=0;
        do {
             number=1000 + (((int) (Math.random() * 1000)) % 9999);
        }while(creditCardRepository.findByNumber(number)!=null);
            return number;
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
