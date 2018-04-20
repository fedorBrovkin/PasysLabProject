package com.epam.labproject.service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.entity.User;
import com.epam.labproject.exception.PasysException;
import com.epam.labproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    private static final String TEST_USER_LOGIN = "alex";
    private static final String ANOTHER_TEST_USER_LOGIN = "another_alex";
    private static final String TEST_USER_PASSWORD = "pwd";
    private static final String TEST_USER_ENCODED_PASSWORD = "enc_pwd";
    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleService roleService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private User user;
    private Role role;

    /**
     * Set up before tests.
     */
    @Before
    public void setUp() {
        user = new User();
        user.setLogin(TEST_USER_LOGIN);
        user.setPassword(TEST_USER_PASSWORD);
        Mockito.when(userRepository.findByLogin(TEST_USER_LOGIN))
                .thenReturn(user);

        role = new Role();
        role.setName(DEFAULT_ROLE_NAME);
        Mockito.when(roleService.findByName(DEFAULT_ROLE_NAME))
                .thenReturn(role);

        Mockito.when(passwordEncoder.encode(TEST_USER_PASSWORD))
                .thenReturn(TEST_USER_ENCODED_PASSWORD);
    }

    @Test
    public void testGetUser() {
        User found = userService.getUser(TEST_USER_LOGIN);

        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        assertThat(found.getLogin())
                .isEqualTo(TEST_USER_LOGIN);
    }

    @Test
    public void testSave() {
        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void testCreateAnotherUser() throws PasysException {
        user.setLogin(ANOTHER_TEST_USER_LOGIN);
        userService.createUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);

        verify(userRepository, times(1)).findByLogin(ANOTHER_TEST_USER_LOGIN);
        verify(passwordEncoder, times(1)).encode(TEST_USER_PASSWORD);
        verify(roleService, times(1)).findByName(DEFAULT_ROLE_NAME);
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = PasysException.class)
    public void testCreateAlreadyExistedUser() throws PasysException {
        userService.createUser(user);

        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(passwordEncoder, never()).encode(any());
        verify(roleService, never()).findByName(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistedUser() {
        userService.delete(TEST_USER_LOGIN);

        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteNotExistedUser() {
        Mockito.when(userRepository.findByLogin(TEST_USER_LOGIN))
                .thenReturn(null);

        userService.delete(TEST_USER_LOGIN);

        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(userRepository, never()).delete(user);
    }

    @Test
    public void testChangeRole() throws PasysException {
        userService.changeRole(TEST_USER_LOGIN, DEFAULT_ROLE_NAME);

        user.setRole(role);
        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(roleService, times(1)).findByName(DEFAULT_ROLE_NAME);
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = PasysException.class)
    public void testChangeRoleWithNotExistedUser() throws PasysException {
        Mockito.when(userRepository.findByLogin(TEST_USER_LOGIN))
                .thenReturn(null);

        userService.changeRole(TEST_USER_LOGIN, DEFAULT_ROLE_NAME);

        user.setRole(role);
        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(roleService, times(1)).findByName(DEFAULT_ROLE_NAME);
        verify(userRepository, never()).save(user);
    }

    @Test(expected = PasysException.class)
    public void testChangeRoleWithNotExistedRole() throws PasysException {
        Mockito.when(roleService.findByName(DEFAULT_ROLE_NAME))
                .thenReturn(null);

        userService.changeRole(TEST_USER_LOGIN, DEFAULT_ROLE_NAME);

        user.setRole(role);
        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(roleService, times(1)).findByName(DEFAULT_ROLE_NAME);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testChangePassword() {
        userService.changePassword(TEST_USER_LOGIN, TEST_USER_PASSWORD);

        user.setPassword(TEST_USER_ENCODED_PASSWORD);
        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(passwordEncoder, times(1)).encode(TEST_USER_PASSWORD);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testChangePasswordWithNotExistedUser() {
        Mockito.when(userRepository.findByLogin(TEST_USER_LOGIN))
                .thenReturn(null);

        userService.changePassword(TEST_USER_LOGIN, TEST_USER_PASSWORD);

        user.setRole(role);
        verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
        verify(passwordEncoder, times(1)).encode(TEST_USER_PASSWORD);
        verify(userRepository, never()).save(user);
    }
}