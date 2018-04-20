package com.epam.labproject.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.epam.labproject.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

  private static final String TEST_USER_LOGIN = "alex";

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testFindByLogin() {
    User alex = new User();
    alex.setLogin(TEST_USER_LOGIN);
    userRepository.save(alex);

    User found = userRepository.findByLogin(TEST_USER_LOGIN);

    assertThat(found.getLogin())
        .isEqualTo(alex.getLogin());
  }
}