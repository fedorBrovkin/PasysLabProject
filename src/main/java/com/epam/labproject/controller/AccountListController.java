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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountListController {

    private final UserService userService;
    private final DataBaseUserDetailsService userDetailsService;
    private final AccountService accountService;

    /**
     * Constructor for class.
     */
    public AccountListController(UserService userService,
                                 DataBaseUserDetailsService userDetailsService,
                                 AccountService accountService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.accountService = accountService;
    }

    /**
     * ShowCard method.
     */
    @GetMapping("/accountList")
    public String showCardList(Model model,
                               @RequestParam(value = "status", required = false) String status) {
        List<Account> accList = accountService
                .findAllByUser(userService
                        .getUser(userDetailsService.getCurrentUsername()));
        model.addAttribute("status", status != null);
        model.addAttribute("accounts", AccountForm.getAccountFormList(accList));
        return "accountList";
    }

    /**
     * Creating account
     */

    @GetMapping("/makeAccount")
    public String makeAccount() {
        accountService.createAccount(userDetailsService.getCurrentUsername());
        return "redirect:/accountList";
    }

}
