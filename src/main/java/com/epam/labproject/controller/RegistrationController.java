package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class RegistrationController {

  private final UserService userService;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Method show registration page
   *
   */

  @GetMapping(value = {"/registration"})
  public String registration(
      @RequestParam(value = "userAlreadyExist", required = false) String error,
      Model model) {
    User user = new User();
    model.addAttribute("error", error != null);
    model.addAttribute("user", user);
    return "registration";
  }

  /**
   * Register user
   *
   */
  @PostMapping(value = {"/registration"})
  public String saveUser(Model model, @ModelAttribute("user") User user) {
    try {
      userService.createUser(user);
    } catch (PasysException e) {
      e.printStackTrace();
      return "redirect:/registration" + e.getMessage();
    }
    return "redirect:/";
  }


}