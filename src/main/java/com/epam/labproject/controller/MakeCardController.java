package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.form.CreateCardForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MakeCardController {

  private final CreditCardService creditCardService;
  private final DataBaseUserDetailsService userDetailsService;
  private final AccountService accountService;
  private final UserService userService;

  /**
   * Constructor.
   * @param creditCardService Injected instance
   * @param userDetailsService Injected instance
   * @param accountService Injected instance
   * @param userService Injected instance
   */
  public MakeCardController(CreditCardService creditCardService,
      DataBaseUserDetailsService userDetailsService,
      AccountService accountService,
      UserService userService) {
    this.creditCardService = creditCardService;
    this.userDetailsService = userDetailsService;
    this.accountService = accountService;
    this.userService = userService;
  }

  /**
   *GetMethod to create card page.
   * @param model Injected instance
   * @return
   */
    @GetMapping("/makeCard")
    public String makeCardPage(Model model,
        @RequestParam(value = "error", required = false) String error) {
        String currentUserLogin = userDetailsService.getCurrentUsername();
        User user = userService.getUser(currentUserLogin);
        List<Account> accountList = accountService.findAllByUserNameAndStatusTrue(currentUserLogin);
        if (CollectionUtils.isEmpty(accountList)) {
            accountService.createAccount(currentUserLogin);
            return "redirect:makeCard";
        }
        CreateCardForm cardForm = new CreateCardForm();
      model.addAttribute("noUser", error != null);
        model.addAttribute("cardForm", cardForm);
        model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));

    return "makeCard";
  }

  /**
   * post method to create Card and Account.
   * @param cardForm  instance
   * @return
   */

    @PostMapping("/createCard")
    public String createCardAndAccount(@ModelAttribute("cardForm") CreateCardForm cardForm) {
        try {
            creditCardService.createCard(cardForm.getLogin(), Integer.parseInt(cardForm.getNumber()));
          return "redirect:cardList?success";
        } catch (PasysException e) {
            return "redirect:/makeCard" + e.getMessage();
        }
    }

}