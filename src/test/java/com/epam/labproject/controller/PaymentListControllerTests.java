package com.epam.labproject.controller;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.User;
import com.epam.labproject.form.CardForm;
import com.epam.labproject.form.PaymentListForm;
import com.epam.labproject.service.CreditCardService;
import com.epam.labproject.service.DataBaseUserDetailsService;
import com.epam.labproject.service.PaymentService;
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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
public class PaymentListControllerTests {

    private static final String TEST_LOGIN = "alex";

    private static final BigDecimal BALANCE = new BigDecimal(100);

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private DataBaseUserDetailsService detailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private CreditCardService cardService;

    private CardForm cardForm;

    private List<PaymentListForm> payments;

    private User user;

    private List<CreditCard> creditCards;

    private CreditCard creditCard;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new PaymentListController(
                paymentService,
                detailsService,
                userService,
                cardService)
        )
                .setViewResolvers(viewResolver)
                .build();
        cardForm = new CardForm();
        payments = new LinkedList<>();

        Account account = new Account();
        account.setNumber(1);
        account.setBalance(BALANCE);

        creditCard = new CreditCard();
        creditCard.setAccount(account);

        creditCards = new ArrayList<>();
        creditCards.add(creditCard);
        user = new User();
        user.setLogin(TEST_LOGIN);
        user.setCards(creditCards);


    }

    @Test
    public void testGetShowSelectCard() throws Exception {
        Mockito.when(detailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);
        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);

        mockMvc.perform(get("/selectCardForPaymentHistory"))
                .andExpect(model().attribute("cards", notNullValue()))
                .andExpect(model().attribute("cards", hasSize(1)))
                .andExpect(model().attribute("cardForm", notNullValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("selectCardForPaymentHistory"));

        verify(detailsService, times(1)).getCurrentUsername();
        verify(userService, times(1)).getUser(any());
    }


    @Test
    public void testPostSelectCard() throws Exception {

        Mockito.when(cardService.findByNumber(anyInt())).thenReturn(creditCard);

        Mockito.when(PaymentListForm.getPaymentList(paymentService.findAllMyPayments(any()), creditCard)).thenReturn(payments);

        mockMvc.perform(post("/selectCardForPaymentHistory")
                    .flashAttr("cardForm", cardForm)
                )
                .andExpect(model().attribute("payments", notNullValue()))
                .andExpect(model().attribute("payments", hasSize(0)))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentList"));

        verify(cardService, times(1)).findByNumber(anyInt());
        verify(paymentService, times(1)).findAllMyPayments(any());
    }

    @Test
    public void testGetShowPaymentList() throws Exception {
        mockMvc.perform(get("/paymentList"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentList"));
    }


}



