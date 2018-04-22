package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.form.AccountForm;
import com.epam.labproject.form.CreateCardForm;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class MakeCardControllerTests {

    private static final String TEST_LOGIN = "alex";
    private static final String TEST_NUMBER = "111";

    private static final BigDecimal BALANCE = new BigDecimal(100);

    @MockBean
    private CreditCardService creditCardService;

    @MockBean
    private DataBaseUserDetailsService userDetailsService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    private Account account;

    private List<Account> accounts;

    private List<AccountForm> accountForms;

    private AccountForm accountForm;

    private CreateCardForm cardForm;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new MakeCardController(
                    creditCardService,
                    userDetailsService,
                    accountService,
                    userService)
                )
                .setViewResolvers(viewResolver)
                .build();

        user = new User();

        account = new Account();
        account.setUser(user);
        account.setBalance(BALANCE);

        accounts = new ArrayList<>();
        accounts.add(account);


        accountForms = new LinkedList<>();

        cardForm = new CreateCardForm();
        cardForm.setLogin(TEST_LOGIN);
        cardForm.setNumber(TEST_NUMBER);

        accountForm = new AccountForm();
        Mockito.when(userDetailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);
        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);
    }


    @Test
    public void testGetMakeCardPageWithEmptyAccountList() throws Exception {

        mockMvc.perform(get("/makeCard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/makeCard"));

        verify(userDetailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(any());
        verify(accountService, times(1)).findAllByUserNameAndStatusTrue(any());
        verify(accountService, times(1)).createAccount(any());
    }

    @Test
    public void testGetMakeCardPageWithNotEmptyAccountList() throws Exception {
        accountForms.add(accountForm);

        Mockito.when(accountService.findAllByUserNameAndStatusTrue(TEST_LOGIN)).thenReturn(accounts);

        mockMvc.perform(get("/makeCard"))
                .andExpect(model().attribute("cardForm", notNullValue()))
                .andExpect(model().attribute("accounts", notNullValue()))
                .andExpect(model().attribute("accounts", hasSize(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("makeCard"));

        verify(userDetailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(any());
        verify(accountService, times(1)).findAllByUserNameAndStatusTrue(TEST_LOGIN);
        verify(accountService, never()).createAccount(any());
    }

    @Test
    public void testPostCreateCardAndAccount() throws Exception {
        mockMvc.perform(post("/createCard")
                    .flashAttr("cardForm", cardForm)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cardList"));

        verify(creditCardService, times(1)).createCard(TEST_LOGIN, Integer.parseInt(TEST_NUMBER));
    }

    @Test
    public void testPostCreateCardAndAccountThrowPasysException() throws Exception {
        doThrow(new PasysException())
                .when(creditCardService).createCard(TEST_LOGIN, Integer.parseInt(TEST_NUMBER));

        mockMvc.perform(
            post("/createCard")
            .flashAttr("cardForm", cardForm)
        )
        .andExpect(status().isOk())
        .andExpect(view().name("makeCard"));

        verify(creditCardService, times(1)).createCard(TEST_LOGIN, Integer.parseInt(TEST_NUMBER));
    }
}

