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
import org.springframework.web.bind.annotation.RequestParam;

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

  @GetMapping(value = {"/makePayment"})
  public String showPaymentPage(Model model,
      @RequestParam(value = "error", required = false) String error) {
    List<CreditCard> cardList = userService.getUser(detailsService.getCurrentUsername()).getCards();
    PaymentForm paymentForm = new PaymentForm();
    model.addAttribute("paymentForm", paymentForm);
    model.addAttribute("cards", CardForm.getCardFormList(cardList));
    setAllert(model, error);
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
      creditCardService.doPayment(paymentForm.getSource(),
          paymentForm.getTarget(),
          paymentForm.getAmount());
    } catch (PasysException e) {
      return "redirect:makePayment" + e.getMessage();
    }
    return "redirect:/userOffice";
  }

  private boolean setAllert(Model model, String error) {
    if (error != null) {
      model.addAttribute("noSource", error.equals(PasysException.CREDIT_CARD_NOT_CHOSEN));
      model.addAttribute("sourceBlocked", error.equals(PasysException.SOURCE_ACCOUNT_IS_BLOCKED));
      model.addAttribute("sourceOutDate",
          error.equals(PasysException.SOURCE_CREDIT_CARD_IS_OUT_DATE));
      model.addAttribute("targetBlocked", error.equals(PasysException.TARGET_ACCOUNT_IS_BLOCKED));
      model.addAttribute("noTarget", error.equals(PasysException.TARGET_CARD_NOT_CHOSEN));
      model.addAttribute("targetOutDate", error.equals(PasysException.TARGET_CARD_IS_OUT_DATE));
      model.addAttribute("noMoney", error.equals(PasysException.NOT_ENOUGHT_MONEY_ON_ACCOUNT));
      return true;
    }
    return false;
  }
}