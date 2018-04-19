package com.epam.labproject.controller;

import com.epam.labproject.service.DataBaseUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
public class UserOfficeControllerTests {

    @MockBean
    private DataBaseUserDetailsService dataBaseUserDetailsService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new UserOfficeController(dataBaseUserDetailsService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetUserOfficeDetails() throws Exception {
        mockMvc.perform(get("/userOffice"))
                .andExpect(status().isOk())
                .andExpect(view().name("userOffice"));
    }

    @Test
    public void testPostMakeCard() throws Exception {
        mockMvc.perform(post("/userOffice"))
                .andExpect(status().isOk())
                .andExpect(view().name("userOffice"));
    }


}
