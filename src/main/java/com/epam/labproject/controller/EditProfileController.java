package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.form.EditProfileForm;
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
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final DataBaseUserDetailsService detailsService;

    public EditProfileController(UserService userService,
                                 PasswordEncoder bCryptPasswordEncoder,
                                 DataBaseUserDetailsService detailsService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.detailsService = detailsService;
    }

    @GetMapping("/editProfilePage")
    public String showEditProfilePage(Model model) {
        EditProfileForm profileForm = new EditProfileForm();
        model.addAttribute("profileForm", profileForm);
        return "editProfilePage";
    }

    @PostMapping("/editProfilePage")
    public String editProfile(Model model, @ModelAttribute("profileForm") EditProfileForm profileForm) {
        User user = userService.getUser(detailsService.getCurrentUsername());
        if (bCryptPasswordEncoder.matches(profileForm.getOldPassworld(), user.getPassword()))
            if (profileForm.getNewPassworld().equals(profileForm.getRepeatPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(profileForm.getNewPassworld()));
                userService.changePassword(detailsService.getCurrentUsername(), profileForm.getNewPassworld());
                return "redirect:/userOffice";
            }
        return "editProfilePage";
    }

}