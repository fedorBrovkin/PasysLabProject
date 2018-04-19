package com.epam.labproject.controller;


import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
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
public class CardListControllerTests {

    @MockBean
    private UserService userService;

    @MockBean
    private DataBaseUserDetailsService dataBaseUserDetailsService;

    @MockBean
    private CreditCardService creditCardService;

    @MockBean
    private AccountService accountService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new CardListController(
                userService,
                dataBaseUserDetailsService,
                creditCardService,
                accountService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetShowListCard() throws Exception {
        mockMvc.perform(get("/cardList"))
                .andExpect(status().isOk())
                .andExpect(view().name("cardList"));
    }
}
