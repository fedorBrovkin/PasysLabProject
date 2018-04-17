package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class RegistrationController {

    private final UserService userService;

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    public RegistrationController(UserService userService){
        this.userService = userService;
    }

  @GetMapping(value = {"/", "/index"})
  public String index(Model model) {

    return "index";
  }

    @GetMapping(value = {"/registration"})
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping(value = {"/registration"})
    public String saveUser(Model model, @ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

}