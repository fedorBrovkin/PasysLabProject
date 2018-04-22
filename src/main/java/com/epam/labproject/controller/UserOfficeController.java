package com.epam.labproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserOfficeController {

    @GetMapping("/userOffice")
    String userOfficeDetails(Model model,
        @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("success", success != null);
        return "userOffice";
    }

}