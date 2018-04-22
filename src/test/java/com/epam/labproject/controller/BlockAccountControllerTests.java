package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.UnblockRequestService;
import com.epam.labproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class BlockAccountControllerTests {

    private static final String TEST_LOGIN = "alex_login";

    @MockBean
    private AccountService accountService;

    @MockBean
    private DataBaseUserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private UnblockRequestService unblockRequestService;

    private Account account;
    private List<Account> accounts;

    private AccountForm accountForm;
    private List<AccountForm> accountForms;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new BlockAccountController(
                accountService,
                userDetailsService,
                userService,
                unblockRequestService)
        )
                .setViewResolvers(viewResolver)
                .build();

        accountForm = new AccountForm();
        accountForms = new LinkedList<>();

        accounts = new ArrayList<>();
        account = new Account();
    }

    @Test
    public void testGetShowSelectAccount() throws Exception {
        mockMvc.perform(get("/blockAccount"))
                .andExpect(model().attribute("accountForm", notNullValue()))
                .andExpect(model().attribute("unblockAccountForm", notNullValue()))
                .andExpect(model().attribute("accounts", notNullValue()))
                .andExpect(model().attribute("accounts", hasSize(0)))
                .andExpect(model().attribute("blockedAccounts", notNullValue()))
                .andExpect(model().attribute("blockedAccounts", hasSize(0)))
                .andExpect(status().isOk())
                .andExpect(view().name("blockAccount"));

        verify(userDetailsService, times(1)).getCurrentUsername();
        verify(accountService, times(1)).findAllByUserNameAndStatusTrue(any());
        verify(accountService, times(1)).findAllByUserByUserNameAndStatusFalse(any());
    }

    @Test
    public void testPostBlockAccount() throws Exception {
        mockMvc.perform(
                post("/blockAccount")
                        .flashAttr("accountForm", accountForm)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("accountList?status"));

        verify(accountService, times(1)).blockAccount(anyInt());
    }

    @Test
    public void testPostUnblockAccount() throws Exception {
        mockMvc.perform(
                post("/unblockAccount")
                        .flashAttr("unblockAccountForm", accountForm)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("accountList?status"));

        verify(unblockRequestService, times(1)).createRequest(anyInt());
    }
}
