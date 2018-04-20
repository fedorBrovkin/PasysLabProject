package com.epam.labproject.service;

import com.epam.labproject.entity.User;
import com.epam.labproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBaseUserDetailsServiceTests {

    private static final String TEST_USER_LOGIN = "alex";
    private static final String TEST_USER_PASSWORD = "pwd";

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setLogin(TEST_USER_LOGIN);
        user.setPassword(TEST_USER_PASSWORD);
        Mockito.when(userRepository.findByLogin(TEST_USER_LOGIN))
                .thenReturn(user);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = userRepository.findByLogin(TEST_USER_LOGIN);
        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        assertThat(user.getLogin())
                .isEqualTo(TEST_USER_LOGIN);
    }
}
