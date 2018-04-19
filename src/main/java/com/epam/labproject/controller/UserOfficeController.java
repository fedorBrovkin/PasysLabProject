package com.epam.labproject.controller;

import com.epam.labproject.service.DataBaseUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserOfficeController {

    private DataBaseUserDetailsService userDetailsService;


    public UserOfficeController(DataBaseUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/userOffice")
    String userOfficeDetails(Model model){
        return "userOffice";
    }

    @PostMapping("/userOffice")
    public String makeCard (){
        return "userOffice";
    }
}