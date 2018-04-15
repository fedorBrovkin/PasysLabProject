package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.service.UserService;
import com.epam.labproject.service.UserServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
class RegistrationController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
  public String index(Model model) {

    return "index";
  }

  @RequestMapping(value = {"/userList"}, method = RequestMethod.GET)
  public String personList(Model model) {

    List<User> users = userService.findAll();
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

    String firstName = user.getLogin();
    String lastName = user.getPassword();

    if (firstName != null && firstName.length() > 0 //
        && lastName != null && lastName.length() > 0) {
      userService.save(user);
      return "redirect:/userList";
    }

    model.addAttribute("hasErrorMessage", true);
    return "registration";
  }
}