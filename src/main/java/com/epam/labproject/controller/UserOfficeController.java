package com.epam.labproject.controller;

import com.epam.labproject.entity.Role;
import com.epam.labproject.service.DataBaseUserDetailsService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserOfficeController {

    private final DataBaseUserDetailsService userDetailsService;

    public UserOfficeController(
        DataBaseUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Display user office
     *
     * @param model - form model
     * @param success - success message
     */
    @GetMapping(value = {"/", "/userOffice"})
    String userOfficeDetails(Model model,
        @RequestParam(value = "success", required = false) String success) {
        String currentUser = userDetailsService.getCurrentUsername();
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        Set<GrantedAuthority> authorities = new HashSet(userDetailsService
            .loadUserByUsername(currentUser).getAuthorities());
        if (authorities.contains(role)) {
            return "redirect:administrator";
        }
        model.addAttribute("success", success != null);
        return "userOffice";
    }

}