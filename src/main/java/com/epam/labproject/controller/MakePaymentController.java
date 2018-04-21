package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.exception.PasysException;
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
public class MakePaymentController {

  private final PaymentService paymentService;
  private final CreditCardService creditCardService;
  private final DataBaseUserDetailsService detailsService;
  private final UserService userService;

  /**
   * Constructor.
   * @param paymentService Injected instance
   * @param creditCardService Injected instance
   * @param detailsService Injected instance
   * @param userService Injected instance
   */
  public MakePaymentController(PaymentService paymentService,
      CreditCardService creditCardService,
      DataBaseUserDetailsService detailsService,
      UserService userService) {
    this.paymentService = paymentService;
    this.creditCardService = creditCardService;
    this.detailsService = detailsService;
    this.userService = userService;
  }

  /**
   * get method.
   * @param model instance
   * @return
   */
  @GetMapping("/makePayment")
  public String showPaymentPage(Model model) {
    List<CreditCard> cardList = userService.getUser(detailsService.getCurrentUsername()).getCards();
    PaymentForm paymentForm = new PaymentForm();
    model.addAttribute("paymentForm", paymentForm);
    model.addAttribute("cards", CardForm.getCardFormList(cardList));

    return "makePayment";
  }

  /**
   * Post method.
   * @param model instance
   * @param paymentForm instance
   * @return
   */
  @PostMapping("/makePayment")
  public String makePayment(Model model, @ModelAttribute("paymentForm") PaymentForm paymentForm) {
    try {
      creditCardService.doPayment(paymentForm.getSourceCard(),
          Integer.parseInt(paymentForm.getTargetCard()),
          Double.parseDouble(paymentForm.getAmount()));
    } catch (PasysException e) {
      e.printStackTrace();
      return e.getMessage();
    }
    return "redirect:/userOffice";
  }
}