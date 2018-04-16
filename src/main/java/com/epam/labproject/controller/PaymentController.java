package com.epam.labproject.controller;

import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    PaymentService paymentService;
    CreditCardService creditCardService;

    public PaymentController(PaymentService paymentService, CreditCardService creditCardService) {
        this.paymentService = paymentService;
        this.creditCardService = creditCardService;
    }

    @GetMapping("/makePayment")
    public String showPaymentPage(){
        return "makePayment";
    }
}
