package com.epam.labproject.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.labproject.entity.Role;
import com.epam.labproject.entity.User;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringRunner.class)
public class RegistrationControllerTests {
    private static final String TEST_LOGIN = "alex_login";
    private static final String TEST_PASSWORD = "pwd";
    private static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";
    private static final String ROLE_USER_NAME = "ROLE_USER";
    private UserDetails userDetails;
    private UserDetails adminUserDetails;

    @MockBean
    private UserService userService;

    @MockBean
    private DataBaseUserDetailsService userDetailsService;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(
                new RegistrationController(userService)
        )
                .setViewResolvers(viewResolver)
                .build();

        Mockito.when(userDetailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);

        Role role = new Role();
        role.setName(ROLE_USER_NAME);
        userDetails = new org.springframework.security.core.userdetails.User(
                TEST_LOGIN,
                TEST_PASSWORD,
                Collections.singleton(role)
        );
        Role adminRole = new Role();
        adminRole.setName(ROLE_ADMIN_NAME);
        adminUserDetails = new org.springframework.security.core.userdetails.User(
                TEST_LOGIN,
                TEST_PASSWORD,
                Collections.singleton(adminRole)
        );

        user = new User();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        user.setRole(role);
    }

    @Test
    public void testGetRegistration() throws Exception {
        mockMvc.perform(
                get("/registration")
                        .flashAttr("userAlreadyExist", new String())
        )
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("error", notNullValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }


    @Test
    public void testPostSaveUserWithExistedUser() throws Exception {

        mockMvc.perform(
                post("/registration")
                        .flashAttr("user", new User())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService, times(1)).createUser(any());
    }
}
