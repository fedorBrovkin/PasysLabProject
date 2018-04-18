package com.epam.labproject.controller;

import com.epam.labproject.service.DataBaseUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserOfficeController {

    private DataBaseUserDetailsService userDetailsService;


    public UserOfficeController(DataBaseUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/userOffice")
    String userOfficeDetails(Model model){
        String currentUser = userDetailsService.getCurrentUsername();
        Set<GrantedAuthority> authirities = new HashSet(userDetailsService.loadUserByUsername(currentUser).getAuthorities());
        for (GrantedAuthority a : authirities) {
            if (a.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:administrator";
            }
        }

        return "userOffice";
    }

    @PostMapping("/userOffice")
    public String makeCard (){
        return "userOffice";
    }
}
