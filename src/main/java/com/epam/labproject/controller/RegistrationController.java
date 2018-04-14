package com.epam.labproject.controller;

import com.epam.labproject.model.entity.User;
//import com.epam.labproject.service.SecurityService;
import com.epam.labproject.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

;

@Controller
class RegistrationController {

    private UserService userService;
//    private SecurityService securityService;
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    public RegistrationController(UserService userService){
        this.userService = userService;
        //this.securityService = securityService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

//    @GetMapping(value = {"/login"})
//    public String login(Model model) {
//
//
//
//        return "login";
//    }


    @GetMapping(value = {"/registration"})
    public String showAddPersonPage(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping(value = {"/registration"})
    public String savePerson(Model model, @ModelAttribute("user") User user) {
        userService.save(user);
        //securityService.autoLogin(user.getLogin(),user.getPassword());
        return "redirect:/";
    }

}