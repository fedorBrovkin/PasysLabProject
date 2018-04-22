package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.service.AccountService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class AdminControllerTests {

    private static final int TEST_ACCOUNT_NUMBER = 1111;

    private static final String TEST_LOGIN = "alex_login";

    private static final BigDecimal BALANCE = new BigDecimal(100);

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    private User user;

    private Account account;
    private List<Account> accounts;

    private AccountForm accountForm;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(
                    new AdminController(accountService, userService)
                )
                .setViewResolvers(viewResolver)
                .build();

        user = new User();

        accountForm = new AccountForm();

        accounts = new ArrayList<>();
        account = new Account();
        account.setBalance(BALANCE);

        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);

        Mockito.when(accountService.findAllByUser(user)).thenReturn(accounts);
    }

    @Test
    public void testGetShowAdministrator() throws Exception {
        mockMvc.perform(get("/administrator"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator"));
    }

    @Test
    public void testGetShowSelectUser() throws Exception {
        mockMvc.perform(get("/admSelectUser"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("admSelectUser"));
    }

    @Test
    public void testPostSelectUserWithNotExistedLogin() throws Exception {
        mockMvc.perform(
                    post("/admSelectUser")
                        .flashAttr("user", user)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admSelectUser?error=noUser"));
    }

    @Test
    public void testPostSelectUserWithEmptyAccountList() throws Exception {
        user.setLogin(TEST_LOGIN);
        mockMvc.perform(
                    post("/admSelectUser")
                    .flashAttr("user", user)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admSelectUser?error=noAccounts"));

        verify(accountService, times(1)).findAllByUser(user);
        verify(userService, times(1)).getUser(TEST_LOGIN);
    }

    @Test
    public void testPostSelectUser() throws Exception {
        user.setLogin(TEST_LOGIN);
        accounts.add(account);
        mockMvc.perform(
                    post("/admSelectUser")
                    .flashAttr("user", user)
                )
                .andExpect(model().attribute("accountForm", notNullValue()))
                .andExpect(model().attribute("accounts", notNullValue()))
                .andExpect(model().attribute("accounts", hasSize(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("admSelectAccount"));

        verify(accountService, times(1)).findAllByUser(user);
        verify(userService, times(1)).getUser(TEST_LOGIN);
    }

    @Test
    public void testGetShowSelectAccount() throws Exception {
        mockMvc.perform(
                    get("/admSelectAccount")
                        .flashAttr("blockedUser", user)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admSelectAccount"));
    }


    @Test
    public void testPostBlockAccount() throws Exception {
        mockMvc.perform(
                    post("/admBlockAccount")
                        .flashAttr("accountForm", accountForm)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrator"));

        verify(accountService, times(1)).changeStatus(anyInt());
    }
}
