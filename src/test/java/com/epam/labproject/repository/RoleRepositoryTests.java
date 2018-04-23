package com.epam.labproject.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.epam.labproject.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTests {

    private static final String TEST_ROLE_NAME = "admin";

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {
        Role admin = new Role();
        admin.setName(TEST_ROLE_NAME);
        roleRepository.save(admin);

        Role found = roleRepository.findByName(TEST_ROLE_NAME);

        assertThat(found.getName())
                .isEqualTo(admin.getName());
    }
}