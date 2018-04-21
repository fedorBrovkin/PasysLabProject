package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.CardForm;
import com.epam.labproject.form.PaymentListForm;
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

  /**
   * Constructor.
   * @param paymentService Injected instance
   * @param detailsService Injected instance
   * @param userService Injected instance
   * @param cardService Injected instance
   */
  public PaymentListController(PaymentService paymentService,
      DataBaseUserDetailsService detailsService,
      UserService userService,
      CreditCardService cardService) {
    this.paymentService = paymentService;
    this.detailsService = detailsService;
    this.userService = userService;
    this.cardService = cardService;
  }

  /**
   * Get method. Create Lists of Card belong to user.
   * @param model instance
   * @return
   */
  @GetMapping("/selectCardForPaymentHistory")
  public String showSelectCard(Model model) {
    User user = userService.getUser(detailsService.getCurrentUsername());
    List<CreditCard> cards = user.getCards();
    model.addAttribute("cards", CardForm.getCardFormList(cards));
    model.addAttribute("cardForm", new CardForm());
    return "selectCardForPaymentHistory";
  }

  /**
   * Post method. To find card.
   * @param model instance
   * @param cardForm instance
   * @return
   */
  @PostMapping("/selectCardForPaymentHistory")
  public String selectCard(Model model, @ModelAttribute CardForm cardForm) {
    CreditCard creditCard = cardService.findByNumber(cardForm.getCardNumber());
    List<PaymentListForm> payments = PaymentListForm
        .getPaymentList(paymentService.findAllMyPayments(creditCard),
            creditCard);
    model.addAttribute("payments", payments);
    return "paymentList";
  }

  /**
   * Showing payment list.
   * @param model isntance
   * @return
   */
  @GetMapping("/paymentList")
  public String showPaymentList(Model model) {

    return "/paymentList";
  }

}
