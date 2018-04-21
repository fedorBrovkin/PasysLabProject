package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

  private final AccountService accountService;
  private final UserService userService;

  public AdminController(AccountService accountService,
      UserService userService) {
    this.accountService = accountService;
    this.userService = userService;
  }

  @GetMapping("/administrator")
  public String showAdministrator(Model model) {
    return "administrator";
  }

  @GetMapping("/admSelectUser")
  public String showSelectUser(Model model) {
    model.addAttribute(new User());
    return "admSelectUser";
  }

  /**
   * This is method to find all the accounts by user from the database.
   * @param model model of the form
   * @param user userVariable
   * @return
   */
  @PostMapping("/admSelectUser")
  public String selectUser(Model model, @ModelAttribute("user") User user) {
    String userLogin = user.getLogin();
    if (userLogin.length() < 0) {
      return "redirect:admSelectUser";
    }
    List<Account> accountList = accountService.findAllByUser(userService.getUser(user.getLogin()));
    if (CollectionUtils.isEmpty(accountList)) {
      return "redirect:administrator";
    }
    model.addAttribute("accountForm", new AccountForm());
    model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));
    return "/admSelectAccount";
  }

  @GetMapping("/admSelectAccount")
  public String showSelectAccount(@ModelAttribute("blockedUser") User user, Model model) {

    return "admSelectAccount";
  }

  @PostMapping("/admBlockAccount")
  public String blockAccount(@ModelAttribute("accountForm") AccountForm accountForm) {
    accountService.changeStatus(accountForm.getAccNumber());
    return "redirect:administrator";
  }
}
