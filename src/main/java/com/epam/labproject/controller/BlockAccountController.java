package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UnblockRequestService;
import com.epam.labproject.service.UserService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlockAccountController {

    private final AccountService accountService;
    private final DataBaseUserDetailsService userDetailsService;
    private final UserService userService;
    private final UnblockRequestService unblockRequestService;

    /**
     * Class constructor.
     */
    public BlockAccountController(AccountService accountService,
                                  DataBaseUserDetailsService userDetailsService,
                                  UserService userService,
                                  UnblockRequestService unblockRequestService) {
        this.accountService = accountService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.unblockRequestService = unblockRequestService;
    }

    /**
     * Mapping two lists of accounts by block status.
     */
    @GetMapping("/blockAccount")
    public String showSelectAccount(Model model) {
        String login = userDetailsService.getCurrentUsername();
        List<Account> accountList = accountService.findAllByUserNameAndStatusTrue(login);
        List<Account> blockedAccountList = accountService.findAllByUserByUserNameAndStatusFalse(login);
        model.addAttribute("accountForm", new AccountForm());
        model.addAttribute("unblockAccountForm", new AccountForm());
        model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));
        model.addAttribute("blockedAccounts", AccountForm.getAccountFormList(blockedAccountList));
        return "blockAccount";
    }

    @PostMapping("/blockAccount")
    public String blockAccount(@ModelAttribute("accountForm") AccountForm accountForm) {
        accountService.blockAccount(accountForm.getAccNumber());
        return "redirect:accountList?status";
    }

    @PostMapping("/unblockAccount")
    public String unblockAccount(@ModelAttribute("unblockAccountForm") AccountForm accountForm) {
        unblockRequestService.createRequest(accountForm.getAccNumber());
        return "redirect:accountList?status";
    }
}
