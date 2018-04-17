package com.epam.labproject.controller;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CardListController {
    private UserService userService;
    private DataBaseUserDetailsService userDetailsService;
    private CreditCardService cardService;
    private AccountService accountService;

    public CardListController(UserService userService,
                              DataBaseUserDetailsService userDetailsService,
                              CreditCardService cardService,
                              AccountService accountService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @GetMapping("/cardList")
    public String showCardList(Model model){
        List<CreditCard> cardList= new ArrayList<>();
        userService.getUser(userDetailsService.getCurrentUsername()).getCards();
        model.addAttribute("cardList", cardList);
        return "cardList";
    }


}
