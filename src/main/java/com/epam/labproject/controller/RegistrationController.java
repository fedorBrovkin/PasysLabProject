package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class RegistrationController {

  @Autowired
  private UserService userService;

  @GetMapping(value = {"/", "/index"})
  public String index(Model model) {

    return "index";
  }

  @GetMapping(value = {"/registration"})
  public String showAddPersonPage(Model model) {

    User user = new User();
    model.addAttribute("user", user);

    return "registration";
  }

  @PostMapping(value = {"/registration"})
  public String savePerson(Model model, @ModelAttribute("user") User user) {

    String firstName = user.getLogin();
    String lastName = user.getPassword();

    if (firstName != null && firstName.length() > 0 //
        && lastName != null && lastName.length() > 0) {
      userService.createUser(user);
      return "redirect:/";
    }

    model.addAttribute("hasErrorMessage", true);
    return "registration";
  }
}