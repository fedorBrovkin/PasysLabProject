package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountListController {

  private final UserService userService;
  private final DataBaseUserDetailsService userDetailsService;
  private final AccountService accountService;

  public AccountListController(UserService userService,
      DataBaseUserDetailsService userDetailsService,
      AccountService accountService) {
    this.userService = userService;
    this.userDetailsService = userDetailsService;
    this.accountService = accountService;
  }

  @GetMapping("/accountList")
  public String showCardList(Model model) {
    List<Account> accList = accountService
        .findAllByUser(userService.getUser(userDetailsService.getCurrentUsername()));
    model.addAttribute("accounts", AccountForm.getAccountFormList(accList));
    return "accountList";
  }

  @GetMapping("/makeAccount")
  public String makeAccount() {
    accountService.createAccount(userDetailsService.getCurrentUsername());
    return "redirect:accountList";
  }


}
