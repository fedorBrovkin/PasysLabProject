package com.epam.labproject.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTests {

    private static final int TEST_ACCOUNT_NUMBER_1 = 12345;

    private static final int TEST_ACCOUNT_NUMBER_2 = 2345;

    private static final int TEST_ACCOUNT_NUMBER_3 = 345;

    private static final int TEST_ACCOUNT_NUMBER = 45;

    private static final BigDecimal TEST_BALANCE_1 = new BigDecimal(100);

    private static final BigDecimal TEST_BALANCE_2 = new BigDecimal(200);

    private static final BigDecimal TEST_BALANCE_3 = new BigDecimal(300);

    private static final String TEST_USER_LOGIN = "alex";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private List<Account> accounts;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setLogin(TEST_USER_LOGIN);
        userRepository.save(user);

        accounts = new ArrayList<>();

        Account account1 = new Account();
        account1.setUser(user);
        account1.setNumber(TEST_ACCOUNT_NUMBER_1);
        account1.setBalance(TEST_BALANCE_1);
        account1.setDateOfCreation(LocalDateTime.now());
        account1.setStatus(true);
        Account savedAccount1 = accountRepository.save(account1);

        Account account2 = new Account();
        account2.setUser(user);
        account2.setNumber(TEST_ACCOUNT_NUMBER_2);
        account2.setBalance(TEST_BALANCE_2);
        account2.setDateOfCreation(LocalDateTime.now());
        account2.setStatus(true);
        Account savedAccount2 = accountRepository.save(account2);

        Account account3 = new Account();
        account3.setUser(user);
        account3.setNumber(TEST_ACCOUNT_NUMBER_3);
        account3.setBalance(TEST_BALANCE_3);
        account3.setDateOfCreation(LocalDateTime.now());
        account3.setStatus(false);
        Account savedAccount3 = accountRepository.save(account3);

        accounts.add(savedAccount1);
        accounts.add(savedAccount2);
        accounts.add(savedAccount3);
    }

    @Test
    public void testFindByNumber() {
        Account account = new Account();
        account.setNumber(TEST_ACCOUNT_NUMBER);
        accountRepository.save(account);

        Account found = accountRepository.findByNumber(TEST_ACCOUNT_NUMBER);

        assertThat(found.getNumber())
                .isEqualTo(account.getNumber());
    }

    @Test
    public void testFindAllByUser() {
        List<Account> foundAccounts = accountRepository.findAllByUser(user);
        assertNotNull(foundAccounts);
        assertThat(foundAccounts, hasSize(3));
        assertThat(foundAccounts, containsInAnyOrder(accounts.get(0), accounts.get(1), accounts.get(2)));
    }

    @Test
    public void testFindAllByUserAndStatusIsTrue() {
        List<Account> foundAccounts = accountRepository.findAllByUserAndStatusIsTrue(user);
        assertNotNull(foundAccounts);
        assertThat(foundAccounts, hasSize(2));
        assertThat(foundAccounts, containsInAnyOrder(accounts.get(0), accounts.get(1)));
    }
}
