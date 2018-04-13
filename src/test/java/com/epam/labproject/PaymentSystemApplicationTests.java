package com.epam.labproject;

import com.epam.labproject.model.entity.User;
import com.epam.labproject.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentSystemApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test(expected = DataIntegrityViolationException.class)
	public void contextLoads() {
		User entity = new User();
		entity.setLogin("test");
		User save = userRepository.save(entity);
		entity.setId(null);
		userRepository.save(entity);
		System.out.println(save.toString());

	}

}
