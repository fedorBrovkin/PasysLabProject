package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
class RegistrationController {

    private final UserService userService;
    private DataBaseUserDetailsService userDetailsService;

    public RegistrationController(UserService userService, DataBaseUserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = {"/", "/index"})
  public String index(Model model) {
        String currentUser = userDetailsService.getCurrentUsername();
        Set<GrantedAuthority> authirities = new HashSet(userDetailsService.loadUserByUsername(currentUser).getAuthorities());
        for (GrantedAuthority a : authirities) {
            if (a.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:administrator";
            }
        }
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
        userService.createUser(user);
        return "redirect:/";
    }

}