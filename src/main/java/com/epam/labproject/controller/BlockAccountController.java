package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BlockAccountController {
    AccountService accountService;
    DataBaseUserDetailsService userDetailsService;
    UserService userService;

    public BlockAccountController(AccountService accountService,
                                  DataBaseUserDetailsService userDetailsService,
                                  UserService userService) {
        this.accountService = accountService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/blockAccount")
    public String showSelectAccount(Model model) {
        User user = userService.getUser(userDetailsService.getCurrentUsername());
        List<Account> accountList = accountService.findAllByUser(user);
        if (CollectionUtils.isEmpty(accountList)) {
            return "redirect:makeCard";
        }
        model.addAttribute("accountForm", new AccountForm());
        model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));
        return "blockAccount";
    }

}
