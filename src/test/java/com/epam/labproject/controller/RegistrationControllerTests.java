//package com.epam.labproject.controller;
//
//import com.epam.labproject.entity.Role;
//import com.epam.labproject.service.DataBaseUserDetailsService;
//import com.epam.labproject.service.UserService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import java.util.Collections;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//
//@RunWith(SpringRunner.class)
//public class RegistrationControllerTests {
//    private static final String TEST_LOGIN = "alex_login";
//    private static final String TEST_PASSWORD = "pwd";
//    private static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";
//    private static final String ROLE_USER_NAME = "ROLE_USER";
//    private UserDetails userDetails;
//    private UserDetails adminUserDetails;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private DataBaseUserDetailsService userDetailsService;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("classpath:templates/");
//        viewResolver.setSuffix(".html");
//
//        mockMvc = MockMvcBuilders.standaloneSetup(
//                    new RegistrationController(userService, userDetailsService)
//                )
//                .setViewResolvers(viewResolver)
//                .build();
//
//        Mockito.when(userDetailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);
//
//        Role role = new Role();
//        role.setName(ROLE_USER_NAME);
//        userDetails = new org.springframework.security.core.userdetails.User(
//                TEST_LOGIN,
//                TEST_PASSWORD,
//                Collections.singleton(role)
//        );
//        Role adminRole = new Role();
//        adminRole.setName(ROLE_ADMIN_NAME);
//        adminUserDetails = new org.springframework.security.core.userdetails.User(
//                TEST_LOGIN,
//                TEST_PASSWORD,
//                Collections.singleton(adminRole)
//        );
//    }
//
//    @Test
//    public void testRootForNotAdmin() throws Exception {
//        Mockito.when(userDetailsService.loadUserByUsername(TEST_LOGIN)).thenReturn(userDetails);
//
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
//
//        verify(userDetailsService, times(1)).getCurrentUsername();
//        verify(userDetailsService, times(1)).loadUserByUsername(TEST_LOGIN);
//    }
//
//    @Test
//    public void testIndexForNotAdmin() throws Exception {
//        Mockito.when(userDetailsService.loadUserByUsername(TEST_LOGIN)).thenReturn(userDetails);
//
//        mockMvc.perform(get("/index"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
//
//        verify(userDetailsService, times(1)).getCurrentUsername();
//        verify(userDetailsService, times(1)).loadUserByUsername(TEST_LOGIN);
//    }
//
//    @Test
//    public void testRootForAdmin() throws Exception {
//        Mockito.when(userDetailsService.loadUserByUsername(TEST_LOGIN)).thenReturn(adminUserDetails);
//
//        mockMvc.perform(get("/"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/administrator"));
//
//        verify(userDetailsService, times(1)).getCurrentUsername();
//        verify(userDetailsService, times(1)).loadUserByUsername(TEST_LOGIN);
//    }
//
//    @Test
//    public void testIndexForAdmin() throws Exception {
//        Mockito.when(userDetailsService.loadUserByUsername(TEST_LOGIN)).thenReturn(adminUserDetails);
//
//        mockMvc.perform(get("/index"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/administrator"));
//
//        verify(userDetailsService, times(1)).getCurrentUsername();
//        verify(userDetailsService, times(1)).loadUserByUsername(TEST_LOGIN);
//    }
//
//    @Test
//    public void testGetRegistration() throws Exception {
//        mockMvc.perform(get("/registration"))
//                .andExpect(model().attribute("user", notNullValue()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("registration"));
//    }
//
//    @Test
//    public void testPostRegistration() throws Exception {
//        mockMvc.perform(post("/registration"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//
//        verify(userService, times(1)).createUser(any());
//    }
//
//    @Test
//    public void testLogout() throws Exception {
//        mockMvc.perform(get("/logout"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }
//}
