package com.epam.labproject.controller;

import com.epam.labproject.model.entity.User;
import com.epam.labproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditProfileController {
    UserService userService;

    public EditProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/editProfilePage")
    public String showEditProfilePage(){
        return "editProfilePage";
    }




}
