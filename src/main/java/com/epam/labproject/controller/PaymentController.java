package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.form.CardForm;
import com.epam.labproject.form.PaymentForm;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.PaymentService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        List<CreditCard> cardList = userService.getUser(detailsService.getCurrentUsername()).getCards();
        PaymentForm paymentForm = new PaymentForm();
        model.addAttribute("paymentForm", paymentForm);
        model.addAttribute("cards", CardForm.getCardFormList(cardList));

        return "makePayment";
    }

    @PostMapping("/makePayment")
    public String makePayment(Model model, @ModelAttribute("paymentForm") PaymentForm paymentForm) {
        creditCardService.doPayment(paymentForm.getSourceCard(),
                Integer.parseInt(paymentForm.getTargetCard()), Double.parseDouble(paymentForm.getAmount()));
        return "redirect:/userOffice";
    }
}
