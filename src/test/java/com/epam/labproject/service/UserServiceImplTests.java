package com.epam.labproject.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.labproject.entity.Role;
import com.epam.labproject.entity.User;
import com.epam.labproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTests {

  private static final String TEST_USER_LOGIN = "alex";
  private static final String TEST_USER_PASSWORD = "pwd";
  private static final String TEST_USER_ENCODED_PASSWORD = "enc_pwd";

  @MockBean
  private UserRepository userRepository;
  @MockBean
  private RoleService roleService;
  @MockBean
  private PasswordEncoder passwordEncoder;

  @Autowired
  @InjectMocks
  private UserService userService = new UserServiceImpl();

  private User user;
  private Role role;

  @Before
  public void setUp() {
    user = new User();
    user.setLogin(TEST_USER_LOGIN);
    user.setPassword(TEST_USER_PASSWORD);
    Mockito.when(userRepository.findByLogin(TEST_USER_LOGIN))
        .thenReturn(user);

    role = new Role();
    role.setName(RoleService.DEFAULT_ROLE_NAME);
    Mockito.when(roleService.findByName(RoleService.DEFAULT_ROLE_NAME))
        .thenReturn(role);

    Mockito.when(passwordEncoder.encode(TEST_USER_PASSWORD))
        .thenReturn(TEST_USER_ENCODED_PASSWORD);
  }

  @Test
  public void getUserByLogin() {
    User found = userService.getUserByLogin(TEST_USER_LOGIN);

    verify(userRepository, times(1)).findByLogin(TEST_USER_LOGIN);
    assertThat(found.getLogin())
        .isEqualTo(TEST_USER_LOGIN);
  }

  @Test
  public void save() {
    userService.save(user);

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(role);

    verify(passwordEncoder, times(1)).encode(TEST_USER_PASSWORD);
    verify(roleService, times(1)).findByName(RoleService.DEFAULT_ROLE_NAME);
    verify(userRepository, times(1)).save(user);
  }
}