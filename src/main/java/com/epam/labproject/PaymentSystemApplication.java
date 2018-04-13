package com.epam.labproject;

import com.epam.labproject.model.entity.Role;
import com.epam.labproject.model.entity.User;
import com.epam.labproject.service.RoleService;
import com.epam.labproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PaymentSystemApplication {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }


  public static void main(String[] args) {

    SpringApplication.run(PaymentSystemApplication.class, args);
  }
}
