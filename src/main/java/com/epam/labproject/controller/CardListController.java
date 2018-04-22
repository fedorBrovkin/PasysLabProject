package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.form.CardForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CardListController {

  private final UserService userService;
  private final DataBaseUserDetailsService userDetailsService;
  private final CreditCardService cardService;
  private final AccountService accountService;

  /**
   * Class constructor.
   * @param userService Injected instance
   * @param userDetailsService Injected instance
   * @param cardService Injected instance
   * @param accountService Injected instance
   */
  public CardListController(UserService userService,
      DataBaseUserDetailsService userDetailsService,
      CreditCardService cardService,
      AccountService accountService) {
    this.userService = userService;
    this.userDetailsService = userDetailsService;
    this.cardService = cardService;
    this.accountService = accountService;
  }

  /**
   * Get method to fill cardList attribute.
   * @param model instance
   * @return
   */

  @GetMapping("/cardList")
  public String showCardList(Model model,
      @RequestParam(value = "success", required = false) String success) {
    List<CreditCard> cardList = userService.getUser(userDetailsService.getCurrentUsername())
        .getCards();
    model.addAttribute("cardList", CardForm.getCardFormList(cardList));
    model.addAttribute("success", success != null);
    return "cardList";
  }


}
