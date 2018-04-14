package com.epam.labproject.controller;

import com.epam.labproject.model.entity.User;
import com.epam.labproject.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

;

@Controller
class RegistrationController {

    private static List<User> users = new ArrayList<User>();

    private UserService userService;

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = {"/userList"}, method = RequestMethod.GET)
    public String personList(Model model) {

        model.addAttribute("users", users);

        return "userList";
    }

    @RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }

    @RequestMapping(value = {"/registration"}, method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/userList";
    }

}