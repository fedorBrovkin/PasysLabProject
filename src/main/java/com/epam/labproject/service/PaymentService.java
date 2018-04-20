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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountService accountService;

    @Autowired
    PaymentService(PaymentRepository paymentRepository, AccountService accountService) {
        this.paymentRepository = paymentRepository;
        this.accountService = accountService;
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    /**
     * Create payment.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createPayment(@Valid Payment payment) throws PasysException {
        if (payment == null) {
            return;
        }

        Account sourceAccount = accountService.findByNumber(payment.getSource().getAccount().getNumber());
        Account targetAccount = accountService.findByNumber(payment.getTarget().getAccount().getNumber());

        BigDecimal paymentBalance = sourceAccount.getBalance();

        if (payment.getAmount().compareTo(paymentBalance) >= 1) {
            throw new PasysException("No funds");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(payment.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(payment.getAmount()));

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

    public List<Payment> findAllByTarget(CreditCard creditCard) {
        List<Payment> allByTarget = paymentRepository.findAllByTarget(creditCard);
        allByTarget.sort(Comparator.comparing(Payment::getTime));
        return allByTarget;
    }

    public List<Payment> findAllMyPayments(CreditCard creditCard) {
        List<Payment> list = findAllBySource(creditCard);
        list.addAll(findAllByTarget(creditCard));
        list.sort(Comparator.comparing(Payment::getTime));
        return list;
    }

    public boolean checkDestination(Payment payment, CreditCard creditCard) {
        return payment.getSource().getNumber() == creditCard.getNumber();
    }

}
