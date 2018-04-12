package com.epam.labproject.Service;

import com.epam.labproject.entity.Payment;
import com.epam.labproject.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository=paymentRepository;
    }
    public void save(Payment payment){
        paymentRepository.save(payment);
    }
}
