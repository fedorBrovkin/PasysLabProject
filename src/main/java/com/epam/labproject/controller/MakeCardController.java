package com.epam.labproject.controller;

import com.epam.labproject.form.CreateCardForm;
import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.User;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MakeCardController {

    private CreditCardService creditCardService;
    private DataBaseUserDetailsService userDetailsService;
    private AccountService accountService;
    private UserService userService;

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
        String currenUserLogin = userDetailsService.getCurrentUsername();
        User user = userService.getUser(currenUserLogin);
        List<Account> accountList = accountService.findAllByUser(user);
        accountService.createAccount(user.getLogin());
        if (CollectionUtils.isEmpty(accountList)){
            accountService.createAccount(currenUserLogin);
            return "redirect:makeCard";
        }
        CreateCardForm cardForm = new CreateCardForm();
        model.addAttribute("cardForm", cardForm);
        model.addAttribute("accounts", accountList);

        return "makeCard";
    }


    @PostMapping("/createCard")
    public String createCardAndAccount(@ModelAttribute("cardForm") CreateCardForm cardForm){
        creditCardService.createCard(cardForm.getLogin(),Integer.parseInt(cardForm.getNumber()));
        return "cardList";
    }
}
