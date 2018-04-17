package com.epam.labproject.controller;

import com.epam.labproject.form.EditProfileForm;
import com.epam.labproject.model.entity.User;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditProfileController {
    private PasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private DataBaseUserDetailsService detailsService;

    public EditProfileController(UserService userService,
                                 PasswordEncoder bCryptPasswordEncoder,
                                 DataBaseUserDetailsService detailsService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.detailsService = detailsService;
    }

    @GetMapping("/editProfilePage")
    public String showEditProfilePage(Model model){
        EditProfileForm profileForm = new EditProfileForm();
        model.addAttribute("profilForm", profileForm);
        return "editProfilePage";
    }

    @PostMapping("/editProfilePage")
    public String editProfile(Model model, @ModelAttribute ("profilForm") EditProfileForm profileForm){
        User user = userService.getUser(detailsService.getCurrentUsername());
        profileForm.setOldPassworld(bCryptPasswordEncoder.encode(profileForm.getOldPassworld()));
        if (user.getPassword().equals(profileForm.getOldPassworld()))
            if (profileForm.getNewPassworld().equals(profileForm.getRepeatPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(profileForm.getNewPassworld()));
                return "redirect:/userOffice";
            }
        return "editProfilePage";
    }


}
