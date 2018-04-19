package com.epam.labproject.controller;

import com.epam.labproject.form.CardForm;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.PaymentService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentListController {
    private final PaymentService paymentService;
    private final DataBaseUserDetailsService detailsService;
    private final UserService userService;
    private final CreditCardService cardService;

    public PaymentListController(PaymentService paymentService, DataBaseUserDetailsService detailsService, UserService userService, CreditCardService cardService) {
        this.paymentService = paymentService;
        this.detailsService = detailsService;
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping("/selectCardForPaymentHistory")
    public String showPaymentList(Model model) {

        model.addAttribute("cardForm", new CardForm());
        return "selectCardForPaymentHistory";
    }

}
