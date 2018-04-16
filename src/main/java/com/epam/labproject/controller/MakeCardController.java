package com.epam.labproject.controller;

import com.epam.labproject.form.CreateCardForm;
import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.User;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MakeCardController {

    CreditCardService creditCardService;
    DataBaseUserDetailsService userDetailsService;
    AccountService accountService;
    UserService userService;

    public MakeCardController(CreditCardService creditCardService,
                              DataBaseUserDetailsService userDetailsService,
                              AccountService accountService,
                              UserService userService) {
        this.creditCardService = creditCardService;
        this.userDetailsService = userDetailsService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/makeCard")
    public String makeCardPage(Model model){
        User user = userService.getUser(userDetailsService.getCurrentUsername());
        List<Account> accountList = accountService.findAllByUser(user);
        accountService.createAccount(user.getLogin());
        if (accountList == null||accountList.isEmpty()){
            accountService.createAccount(userDetailsService.getCurrentUsername());
            return "redirect:makeCard";
        }
        model.addAttribute("accounts", accountList);
        CreateCardForm cardForm = new CreateCardForm();
        model.addAttribute("cardForm", cardForm);
        return "makeCard";
    }


    @PostMapping("createCard")
    public String createCardAndAccount(@ModelAttribute("createCardForm") CreateCardForm cardForm){
        creditCardService.createCard(cardForm.getLogin(),cardForm.getNumber());
        return "cardList";
    }
}