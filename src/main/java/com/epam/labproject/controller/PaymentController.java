package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.form.PaymentForm;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.PaymentService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final DataBaseUserDetailsService detailsService;
    private final UserService userService;

    public PaymentController(PaymentService paymentService,
                             CreditCardService creditCardService,
                             DataBaseUserDetailsService detailsService,
                             UserService userService) {
        this.paymentService = paymentService;
        this.creditCardService = creditCardService;
        this.detailsService = detailsService;
        this.userService = userService;
    }

    @GetMapping("/makePayment")
    public String showPaymentPage(Model model) {


        List<CreditCard> cards;

        PaymentForm paymentForm = new PaymentForm();
        model.addAttribute("paymentForm", paymentForm);
        return "makePayment";
    }

    @PostMapping("/makePayment")
    public String makePayment(@RequestAttribute("paymentForm") PaymentForm paymentForm) {

        return "redirect:userOffice";
    }
}
