package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.CardForm;
import com.epam.labproject.form.PaymentForm;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.PaymentService;
import com.epam.labproject.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentListController {
    private final PaymentService paymentService;
    private final DataBaseUserDetailsService detailsService;
    private final UserService userService;
    private final CreditCardService cardService;

    public PaymentListController(PaymentService paymentService,
                                 DataBaseUserDetailsService detailsService,
                                 UserService userService,
                                 CreditCardService cardService) {
        this.paymentService = paymentService;
        this.detailsService = detailsService;
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping("/selectCardForPaymentHistory")
    public String showSelectCard(Model model) {
        User user = userService.getUser(detailsService.getCurrentUsername());
        List<CreditCard> cards = user.getCards();
        model.addAttribute("cards", CardForm.getCardFormList(cards));
        model.addAttribute("cardForm", new CardForm());
        return "selectCardForPaymentHistory";
    }

    @PostMapping("/selectCardForPaymentHistory")
    public String selectCard(Model model, @ModelAttribute("cardForm") CardForm cardForm) {
        CreditCard creditCard = cardService.findByNumber(cardForm.getCardNumber());
      List<PaymentForm> payments = PaymentForm
          .getPaymentList(paymentService.findAllMyPayments(creditCard),
            creditCard);
        model.addAttribute("payments", payments);
        return "paymentList";
    }

    @GetMapping("/paymentList")
    public String showPaymentList(Model model) {

        return "paymentList";
    }

}
