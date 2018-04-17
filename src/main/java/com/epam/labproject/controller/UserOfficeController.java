package com.epam.labproject.controller;

import com.epam.labproject.service.DataBaseUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserOfficeController {

    private DataBaseUserDetailsService userDetailsService;

    @Value("${User.welcome.Messege}")
    private String message;

    public UserOfficeController(DataBaseUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/userOffice")
    String userOfficeDetails(Model model){
        model.addAttribute("message",message + " " + userDetailsService.getCurrentUsername());
        return "userOffice";
    }

    @PostMapping("/userOffice")
    public String makeCard (){
        return "userOffice";
    }
}
