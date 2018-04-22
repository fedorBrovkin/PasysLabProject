package com.epam.labproject.controller;

import com.epam.labproject.entity.User;
import com.epam.labproject.form.CardForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
public class CardListControllerTests {

    private static final String TEST_LOGIN = "alex_login";

    @MockBean
    private UserService userService;

    @MockBean
    private DataBaseUserDetailsService userDetailsService;

    @MockBean
    private CreditCardService cardService;

    @MockBean
    private AccountService accountService;

    private CardForm cardForm;
    private List<CardForm> cardForms;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new CardListController(
                    userService,
                    userDetailsService,
                    cardService,
                    accountService)
                )
                .setViewResolvers(viewResolver)
                .build();

        cardForms = new ArrayList<>();
        cardForm = new CardForm();
        cardForms.add(cardForm);

        user = new User();
        user.setLogin(TEST_LOGIN);

        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);
        Mockito.when(userDetailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);
    }

    @Test
    public void testGetShowCardList() throws Exception {
        mockMvc.perform(get("/cardList"))
                .andExpect(model().attribute("cardList", notNullValue()))
                .andExpect(model().attribute("cardList", hasSize(0)))
                .andExpect(status().isOk())
                .andExpect(view().name("cardList"));

        verify(userDetailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(TEST_LOGIN);
    }
}
