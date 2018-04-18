package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    private static User blockUser;
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

    @PostMapping("/admSelectUser")
    public String blockAccount(@ModelAttribute("user") User user) {
        AdminController.blockUser = userService.getUser(user.getLogin());
        return "redirect:admSelectAccount";
    }

    @GetMapping("/admSelectAccount")
    public String showSelectAccount(Model model) {
        List<Account> accountList = accountService.findAllByUser(AdminController.blockUser);
        if (CollectionUtils.isEmpty(accountList)) {
            accountService.createAccount(AdminController.blockUser.getLogin());
            return "redirect:makeCard";
        }
        model.addAttribute("accountForm", new AccountForm());
        model.addAttribute("accounts", AccountForm.getAccountFormList(accountList));
        return "admSelectAccount";
    }

    @PostMapping("/admSelectAccount")
    public String selectUser(@ModelAttribute("accountForm") AccountForm accountForm) {

        return "redirect:administrator";
    }
}
