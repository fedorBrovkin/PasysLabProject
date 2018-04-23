package com.epam.labproject.service;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.repository.CreditCardRepository;
import com.epam.labproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditCardServiceTests {

    private static final String TEST_USER_LOGIN = "alex";

    private static final int TEST_CREDIT_CARD_NUMBER = 54321;

    private static final int TEST_ACCOUNT_NUMBER = 1111;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CreditCardService creditCardService;

    private CreditCard creditCard;

    private User user;

    private Account account;

    @Before
    public void setUp() {
        user = new User();
        user.setLogin(TEST_USER_LOGIN);
        creditCard = new CreditCard();
        creditCard.setUser(user);
        creditCard.setNumber(TEST_CREDIT_CARD_NUMBER);
        creditCard.setExpirationDate(LocalDateTime.now());
        account = new Account();
        account.setUser(user);
        account.setNumber(TEST_ACCOUNT_NUMBER);
        account.setStatus(false);
    }

    @Test(expected = PasysException.class)
    public void testCreateCardNotExistedAccount() throws PasysException {
        Mockito.when(accountService.findByNumber(TEST_ACCOUNT_NUMBER))
                .thenReturn(null);

        creditCardService.createCard(TEST_USER_LOGIN, TEST_ACCOUNT_NUMBER);

        verify(userService, times(1)).getUser(TEST_USER_LOGIN);
        verify(accountService, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);
        verify(creditCardRepository, never()).save(any());
    }

    @Test(expected = PasysException.class)
    public void testCreateCardNotExistedUser() throws PasysException {
        Mockito.when(userService.getUser(TEST_USER_LOGIN))
                .thenReturn(null);

        creditCardService.createCard(TEST_USER_LOGIN, TEST_ACCOUNT_NUMBER);

        verify(userService, times(1)).getUser(TEST_USER_LOGIN);
        verify(accountService, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);
        verify(creditCardRepository, never()).save(any());
    }

    @Test(expected = PasysException.class)
    public void testCreateCardWithFalseAccountStatus() throws PasysException {
        creditCardService.createCard(TEST_USER_LOGIN, TEST_ACCOUNT_NUMBER);

        verify(userService, times(1)).getUser(TEST_USER_LOGIN);
        verify(accountService, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);
        verify(creditCardRepository, never()).save(any());

    }

    @Test(expected = PasysException.class)
    public void testCreateCard() throws PasysException {
        account.setStatus(true);
        creditCardService.createCard(TEST_USER_LOGIN, TEST_ACCOUNT_NUMBER);

        verify(userService, times(1)).getUser(TEST_USER_LOGIN);
        verify(accountService, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);
        verify(creditCardRepository, times(1)).save(creditCard);

    }

    @Test
    public void testSave() {
        creditCardService.save(creditCard);

        verify(creditCardRepository, times(1)).save(creditCard);
        verify(userService, times(1)).save(user);
    }

    @Test
    public void testDeleteNotExistedCreditCard() {
        creditCard = null;
        creditCardService.delete(creditCard);

        verify(creditCardRepository, never()).delete(creditCardRepository.findByNumber(TEST_CREDIT_CARD_NUMBER));
    }

    @Test
    public void testDeleteExistedCreditCard() {
        creditCardService.delete(creditCard);

        verify(creditCardRepository, times(1)).delete(creditCardRepository.findByNumber(TEST_CREDIT_CARD_NUMBER));
    }

    @Test
    public void testCheckStatusIsTrue() {
        creditCard.setExpirationDate(creditCard.getExpirationDate().plusYears(5));
        assertTrue(creditCardService.checkStatus(creditCard));
    }

    @Test
    public void testCheckStatusIsFalse() {
        assertFalse(creditCardService.checkStatus(creditCard));
    }

    @Test
    public void testFindByNumber() {
        Mockito.when(creditCardRepository.findByNumber(TEST_CREDIT_CARD_NUMBER))
                .thenReturn(creditCard);

        CreditCard found = creditCardService.findByNumber(TEST_CREDIT_CARD_NUMBER);

        verify(creditCardRepository, times(1)).findByNumber(TEST_CREDIT_CARD_NUMBER);

        assertThat(found.getNumber())
                .isEqualTo(creditCard.getNumber());
    }
}
