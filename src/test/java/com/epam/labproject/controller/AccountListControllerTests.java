package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UserService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class AccountListControllerTests {

    private static final String TEST_LOGIN = "alex_login";
    private static final BigDecimal BALANCE = new BigDecimal(100);
    private UserDetails userDetails;
    private UserDetails adminUserDetails;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    @MockBean
    private DataBaseUserDetailsService userDetailsService;

    private User user;

    private List<Account> accounts;

    private Account account;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(
                new AccountListController(userService, userDetailsService, accountService)
        )
                .setViewResolvers(viewResolver)
                .build();

        Mockito.when(userDetailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);

        user = new User();
        user.setLogin(TEST_LOGIN);
        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);

        accounts = new ArrayList<>();
        account = new Account();
        account.setUser(user);
        account.setBalance(BALANCE);
        accounts.add(account);

        Mockito.when(accountService.findAllByUser(user)).thenReturn(accounts);
    }

    @Test
    public void testShowCardList() throws Exception {
        mockMvc.perform(get("/accountList"))
                .andExpect(model().attribute("accounts", notNullValue()))
                .andExpect(model().attribute("accounts", hasSize(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("accountList"));

        verify(userDetailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(TEST_LOGIN);
        verify(accountService, times(1)).findAllByUser(user);
    }

    @Test
    public void testMakeAccount() throws Exception {
        mockMvc.perform(get("/makeAccount"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accountList"));

        verify(userDetailsService, times(1)).getCurrentUsername();
        verify(accountService, times(1)).createAccount(TEST_LOGIN);
    }
}
