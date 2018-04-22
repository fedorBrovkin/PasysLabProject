package com.epam.labproject.controller;

import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

  private final UserService userService;
  private final DataBaseUserDetailsService userDetailsService;

  public MainController(UserService userService, DataBaseUserDetailsService userDetailsService) {
    this.userService = userService;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Default page
   */
  @GetMapping(value = {"/", "/index"})
  public String index(Model model) {
    String currentUser = userDetailsService.getCurrentUsername();
    Set<GrantedAuthority> authirities = new HashSet(userDetailsService
        .loadUserByUsername(currentUser).getAuthorities());
    for (GrantedAuthority a : authirities) {
      if (a.getAuthority().equals("ROLE_ADMIN")) {
        return "redirect:administrator";
      }
    }
    return "index";
  }

  /**
   * Login page
   *
   * @param error - error message
   * @param model - form model
   */
  @RequestMapping("/login")
  public String getLogin(@RequestParam(value = "error", required = false) String error,
      Model model) {
    model.addAttribute("error", error != null);
    return "login";
  }

  /**
   * Logout page
   */
  @GetMapping(value = "/logout")
  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout";
  }
}
