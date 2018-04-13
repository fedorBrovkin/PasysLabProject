package com.epam.labproject.controller;

import java.util.ArrayList;
import com.epam.labproject.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
class RegistrationController {

    private static List<User> users = new ArrayList<User>();

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

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
    public String savePerson(Model model, //
                             @ModelAttribute("user") User user) {

        String firstName = user.getLogin();
        String lastName = user.getPassword();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            users.add(user);

            return "redirect:/userList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "registration";
    }

}