package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/administrator")
    public String showAdministratot(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "administrator";
    }

    @PostMapping("/administrator")
    public String choseUser(Model model, @ModelAttribute("user") User user) {
        List<Account> accountList = accountService.findAllByUser(user);
        if (CollectionUtils.isEmpty(accountList)) {

        }
        AccountForm accountForm = new AccountForm();
        model.addAttribute("accountForm", accountForm);
        model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));

        return "administrator";
    }

    @PostMapping("/blockAccount")
    public String blockAccount(@ModelAttribute("accountForm") AccountForm accountForm) {
        accountService.changeStatus(accountForm.getAccNumber());
        return "/administrator";
    }

}
