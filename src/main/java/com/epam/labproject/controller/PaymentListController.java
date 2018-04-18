package com.epam.labproject.controller;

import com.epam.labproject.entity.Payment;
import com.epam.labproject.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PaymentListController {
    private final PaymentService paymentService;

    public PaymentListController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/paymentList")
    public String showPaymentList(Model model) {
        List<Payment> paymentList;
        //model.addAttribute();
        return "paymentList";
    }
}
