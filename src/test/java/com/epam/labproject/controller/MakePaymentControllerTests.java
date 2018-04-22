//package com.epam.labproject.controller;
//
//import com.epam.labproject.entity.CreditCard;
//import com.epam.labproject.entity.User;
//import com.epam.labproject.exception.PasysException;
//import com.epam.labproject.form.PaymentForm;
//import com.epam.labproject.service.CreditCardService;
//import com.epam.labproject.service.DataBaseUserDetailsService;
//import com.epam.labproject.service.PaymentService;
//import com.epam.labproject.service.UserService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.core.IsNull.notNullValue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyDouble;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//public class MakePaymentControllerTests {
//
//    private static final String TEST_LOGIN = "alex";
//
//    @MockBean
//    private PaymentService paymentService;
//
//    @MockBean
//    private CreditCardService creditCardService;
//
//    @MockBean
//    private DataBaseUserDetailsService detailsService;
//
//    @MockBean
//    private UserService userService;
//
//    private List<CreditCard> cards;
//
//    private User user;
//
//    private PaymentForm paymentForm;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("classpath:templates/");
//        viewResolver.setSuffix(".html");
//
//        mockMvc = MockMvcBuilders.standaloneSetup(new MakePaymentController(
//                    paymentService,
//                    creditCardService,
//                    detailsService,
//                    userService)
//                )
//                .setViewResolvers(viewResolver)
//                .build();
//
//        cards = new ArrayList<>();
//        user = new User();
//        user.setLogin(TEST_LOGIN);
//        user.setCards(cards);
//
//        paymentForm = new PaymentForm();
//        paymentForm.setAmount("100.0");
//        paymentForm.setSourceCard(1);
//        paymentForm.setTargetCard("2");
//
//    }
//
//    @Test
//    public void testGetShowPaymentPage() throws Exception {
//        Mockito.when(detailsService.getCurrentUsername()).thenReturn(TEST_LOGIN);
//        Mockito.when(userService.getUser(TEST_LOGIN)).thenReturn(user);
//
//        mockMvc.perform(get("/makePayment"))
//                .andExpect(model().attribute("paymentForm", notNullValue()))
//                .andExpect(model().attribute("cards", notNullValue()))
//                .andExpect(model().attribute("cards", hasSize(0)))
//                .andExpect(status().isOk())
//                .andExpect(view().name("makePayment"));
//
//        verify(detailsService, times(1)).getCurrentUsername();
//        verify(userService, times(1)).getUser(any());
//    }
//
//    @Test
//    public void testPostMakePaymentWithPasysExeprion() throws Exception {
//        doThrow(new PasysException()).when(creditCardService).doPayment(anyInt(), anyInt(), anyDouble());
//
//        mockMvc.perform(post("/makePayment")
//                    .flashAttr("paymentForm", paymentForm));
//
//        verify(creditCardService, times(1)).doPayment(anyInt(), anyInt(), anyDouble());
//    }
//
//    @Test
//    public void testPostMakePayment() throws Exception {
//        mockMvc.perform(post("/makePayment")
//                    .flashAttr("paymentForm", paymentForm)
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/userOffice"));
//
//        verify(creditCardService, times(1)).doPayment(anyInt(), anyInt(), anyDouble());
//    }
//}
