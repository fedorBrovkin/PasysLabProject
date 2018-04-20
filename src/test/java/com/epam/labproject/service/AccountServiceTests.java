package com.epam.labproject.service;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.repository.AccountRepository;
import com.epam.labproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTests {

    private static final int TEST_ACCOUNT_NUMBER = 1111;

    private static final BigDecimal TEST_BALANCE = new BigDecimal(100);

    private static final String TEST_USER_LOGIN = "alex";

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private AccountService accountService;

    private List<Account> accounts;
    private Account account;//can make two accounts mock and not mock for differ methods
    private boolean status;
    private User user;

    @Before
    public void setUp() {
        account = new Account();
        account.setNumber(TEST_ACCOUNT_NUMBER);
        account.setStatus(false);
        account.setBalance(TEST_BALANCE);
        status = account.getStatus();
        Mockito.when(accountRepository.findByNumber(TEST_ACCOUNT_NUMBER))
                .thenReturn(account);

        user = new User();
        user.setLogin(TEST_USER_LOGIN);

        accounts = new ArrayList<>();

        Account account1 = new Account();
        account1.setStatus(true);
        account1.setUser(user);

        Account account2 = new Account();
        account2.setStatus(true);
        account2.setUser(user);

        Account account3 = new Account();
        account3.setStatus(false);
        account3.setUser(user);

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        Mockito.when(accountRepository.findAllByUser(user))
                .thenReturn(accounts);

        Mockito.when(accountRepository.findAllByUserAndStatusIsTrue(userService.getUser(TEST_USER_LOGIN)))
                .thenReturn(accounts);

    }

    @Test
    public void testSave() {
        accountService.save(account);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDelete() {
        accountService.delete(account);

        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    public void testFindByName() {
        Account found = accountService.findByNumber(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

        assertThat(found.getNumber())
                .isEqualTo(TEST_ACCOUNT_NUMBER);
    }

    @Test
    public void testFindAllByUserNameAndStatusTrue() {
        List<Account> allByUser = accountService.findAllByUserNameAndStatusTrue(TEST_USER_LOGIN);

        assertNotNull(allByUser);
        verify(accountRepository, times(1)).findAllByUserAndStatusIsTrue(userService.getUser(TEST_USER_LOGIN));
        assertThat(allByUser, hasSize(3));

        assertTrue(allByUser.get(0).equals(accounts.get(0)));
        assertTrue(allByUser.get(1).equals(accounts.get(1)));
        assertTrue(allByUser.get(2).equals(accounts.get(2)));
    }

    @Test
    public void testFindAllByUser() {
        List<Account> allByUser = accountService.findAllByUser(user);

        assertNotNull(allByUser);
        verify(accountRepository, times(1)).findAllByUser(user);

        assertThat(allByUser, hasSize(3));

        assertTrue(allByUser.get(0).equals(accounts.get(0)));
        assertTrue(allByUser.get(1).equals(accounts.get(1)));
        assertTrue(allByUser.get(2).equals(accounts.get(2)));
    }


    @Test(expected = PasysException.class)
    public void testGiveMoneyNotExistedAccount() throws PasysException {
        Mockito.when(accountRepository.findByNumber(TEST_ACCOUNT_NUMBER))
                .thenReturn(null);

        accountService.giveMoney(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);
    }

    @Test(expected = PasysException.class)
    public void testGiveMoneyNotBlockedAccount() throws PasysException {
        Mockito.when(accountRepository.findByNumber(TEST_ACCOUNT_NUMBER))
                .thenReturn(account);

        accountService.giveMoney(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

    }

    @Test
    public void testGiveMoney() throws PasysException {
        account.setStatus(true);
        Mockito.when(accountRepository.findByNumber(TEST_ACCOUNT_NUMBER))
                .thenReturn(account);

        accountService.giveMoney(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testChangeStatus() {
        accountService.changeStatus(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).save(account);

        assertThat(account.getStatus())
                .isNotEqualTo(status);
    }

    @Test
    public void testBlockAccount() {
        accountService.blockAccount(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).save(account);

        assertThat(account.getStatus())
                .isEqualTo(false);
    }

    @Test
    public void testIsActive() {
        boolean status = accountService.isActive(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

        assertThat(status).isEqualTo(account.getStatus());
    }

    @Test
    public void testGetBalanceExistedAccount() {
        double balance = accountService.getBalance(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

        assertThat(balance)
                .isNotEqualTo(account.getBalance());


    }

    @Test
    public void testGetBalanceNotExistedAccount() {
        Mockito.when(accountRepository.findByNumber(TEST_ACCOUNT_NUMBER))
                .thenReturn(null);

        double balance = accountService.getBalance(TEST_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findByNumber(TEST_ACCOUNT_NUMBER);

        assertThat(balance).isEqualTo(0.0);
    }
}
