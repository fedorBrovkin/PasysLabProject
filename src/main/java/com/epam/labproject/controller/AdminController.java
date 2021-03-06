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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AccountService accountService;
    private final UserService userService;

    public AdminController(AccountService accountService,
                           UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

  /**
   * Displaying administarators page
   *
   * @param model - form model
   * @param status - message
   */

  @GetMapping("/administrator")
  public String showAdministrator(Model model,
      @RequestParam(value = "status", required = false) String status) {
    model.addAttribute("status", status != null);
    return "administrator";
  }
  /**
   * Select user for look at his accounts
   *
   * @param model -  form model
   * @param error - message
   */

  @GetMapping("/admSelectUser")
  public String showSelectUser(Model model,
      @RequestParam(value = "error", required = false) String error) {
    model.addAttribute(new User());
    if (error != null) {
      model.addAttribute("noUser", error.equals("noUser"));
      model.addAttribute("noAccount", error.equals("noAccounts"));
      model.addAttribute(new User());
    }
    return "admSelectUser";
  }

  /**
   * This is method to find all the accounts by user from the database.
   *
   */
  @PostMapping("/admSelectUser")
  public String selectUser(Model model, @ModelAttribute("user") User user) {
    String userLogin = user.getLogin();
    user = userService.getUser(userLogin);
    if (user == null) {
      return "redirect:admSelectUser?error=noUser";
    }
    List<Account> accountList = accountService.findAllByUser(user);
    if (CollectionUtils.isEmpty(accountList)) {
      return "redirect:admSelectUser?error=noAccounts";
    }
    model.addAttribute("accountForm", new AccountForm());
    model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));
    return "/admSelectAccount";
  }

  /**
   * Display users accounts
   */

  @GetMapping("/admSelectAccount")
  public String showSelectAccount(@ModelAttribute("blockedUser") User user, Model model) {

        return "admSelectAccount";
    }

  /**
   * Changing account status
   *
   */

  @PostMapping("/admBlockAccount")
  public String blockAccount(@ModelAttribute("accountForm") AccountForm accountForm) {
    accountService.changeStatus(accountForm.getAccNumber());
    return "redirect:administrator?status";
  }
}
