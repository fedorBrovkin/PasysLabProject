package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.form.EditProfileForm;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class EditProfileControllerTests {

    private static final String TEST_LOGIN = "alex";

    private static final String TEST_PASSWORD = "pwd";
    private static final String TEST_NEW_PASSWORD = "new_pwd";

    private static final String TEST_ANOTHER_PASSWORD = "other_pwd";

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private DataBaseUserDetailsService detailsService;

    private EditProfileForm profileForm;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new EditProfileController(
                userService,
                bCryptPasswordEncoder,
                detailsService)
        )
                .setViewResolvers(viewResolver)
                .build();

        profileForm = new EditProfileForm();
        profileForm.setOldPassworld(TEST_PASSWORD);
        profileForm.setNewPassworld(TEST_NEW_PASSWORD);
        profileForm.setRepeatPassword(TEST_NEW_PASSWORD);

        user = new User();
        user.setPassword(TEST_PASSWORD);

        Mockito.when(detailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);
        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);
    }

    @Test
    public void testGetShowEditProfilePage() throws Exception {
        mockMvc.perform(get("/editProfilePage"))
                .andExpect(model().attribute("profileForm", notNullValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("editProfilePage"));
    }

    @Test
    public void testPostEditProfileNotMatchesPasswords() throws Exception {
        Mockito.when(bCryptPasswordEncoder.matches(
                    profileForm.getOldPassworld(),
                    user.getPassword())
                )
                .thenReturn(false);

        mockMvc.perform(
                post("/editProfilePage")
                        .flashAttr("profileForm", profileForm)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("editProfilePage"));

        verify(detailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(TEST_LOGIN);
        verify(bCryptPasswordEncoder, times(1))
                .matches(profileForm.getOldPassworld(), user.getPassword());
    }

    @Test
    public void testPostEditProfileMatchesPasswordsAndNotEqualsPasswords() throws Exception {
        Mockito.when(bCryptPasswordEncoder.matches(
                    profileForm.getOldPassworld(),
                    user.getPassword())
                )
                .thenReturn(true);
        profileForm.setRepeatPassword(TEST_ANOTHER_PASSWORD);
        mockMvc.perform(
                    post("/editProfilePage")
                        .flashAttr("profileForm", profileForm)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("editProfilePage"));

        verify(detailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(TEST_LOGIN);
        verify(bCryptPasswordEncoder, times(1))
                .matches(TEST_PASSWORD, TEST_PASSWORD);
    }

    @Test
    public void testPostEditProfileMatchesPasswordsAndEqualsPasswords() throws Exception {
        Mockito.when(bCryptPasswordEncoder.matches(
                profileForm.getOldPassworld(),
                user.getPassword())
        )
                .thenReturn(true);

        mockMvc.perform(
                    post("/editProfilePage")
                    .flashAttr("profileForm", profileForm)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userOffice"));

        verify(detailsService, times(2)).getCurrentUsername();
        verify(userService, times(1)).getUser(TEST_LOGIN);
        verify(bCryptPasswordEncoder, times(1)).matches(TEST_PASSWORD, TEST_PASSWORD);
        verify(bCryptPasswordEncoder, times(1)).encode(TEST_NEW_PASSWORD);
        verify(userService, times(1)).changePassword(TEST_LOGIN, TEST_NEW_PASSWORD);
    }
}
