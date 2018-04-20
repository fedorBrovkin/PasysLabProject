package com.epam.labproject.service;

import com.epam.labproject.entity.Role;
import com.epam.labproject.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTests {

    private static final String TEST_ROLE_NAME = "user";

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    private Role role;

    @Before
    public void setUp() {
        role = new Role();
        role.setName(TEST_ROLE_NAME);
        Mockito.when(roleRepository.findByName(TEST_ROLE_NAME))
                .thenReturn(role);
    }

    @Test
    public void testSaveNullRole() {
        roleService.save(null);

        verify(roleRepository, never()).save(any());
    }

    @Test
    public void testSaveRoleWithEmptyName() {
        role.setName("");
        Mockito.when(roleRepository.findByName(TEST_ROLE_NAME))
                .thenReturn(role);

        roleService.save(role);

        verify(roleRepository, never()).save(any());
    }


    @Test
    public void testSaveExistedRole() {
        roleService.save(role);

        verify(roleRepository, never()).save(any());
    }

    @Test
    public void testSaveRole() {
        Mockito.when(roleRepository.findByName(TEST_ROLE_NAME))
                .thenReturn(null);

        roleService.save(role);

        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testFindByName() {
        Mockito.when(roleRepository.findByName(TEST_ROLE_NAME))
                .thenReturn(role);
        Role found = roleService.findByName(TEST_ROLE_NAME);

        verify(roleRepository, times(1)).findByName(TEST_ROLE_NAME);

        assertThat(found.getName())
                .isEqualTo(TEST_ROLE_NAME);
    }

    @Test
    public void testDeleteNullRole() {
        roleService.delete(null);

        verify(roleRepository, never()).findByName(any());
        verify(roleRepository, never()).delete(any());
    }

    @Test
    public void testDeleteNotExistedRole() {
        Mockito.when(roleRepository.findByName(TEST_ROLE_NAME))
                .thenReturn(null);

        roleService.delete(role);

        verify(roleRepository, times(1)).findByName(TEST_ROLE_NAME);
        verify(roleRepository, never()).delete(any());
    }

    @Test
    public void testDeleteExistedRole() {
        roleService.delete(role);

        verify(roleRepository, times(1)).findByName(TEST_ROLE_NAME);
        verify(roleRepository, times(1)).delete(role);
    }
}
