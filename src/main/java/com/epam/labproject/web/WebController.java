package com.epam.labproject.web;

//import com.epam.labproject.model.Customer;
//import com.epam.labproject.repository.CustomerRepository;
//import com.epam.labproject.model.entity.Account;
//import com.epam.labproject.repository.AccountRepository;
//import com.epam.labproject.model.entity.CreditCard;
//import com.epam.labproject.repository.CreditCardRepository;
//import com.epam.labproject.model.entity.Payment;
//import com.epam.labproject.repository.PaymentRepository;
import com.epam.labproject.model.entity.Role;
import com.epam.labproject.repository.RoleRepository;
//import com.epam.labproject.model.entity.User;
//import com.epam.labproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

//  @Autowired
//  CustomerRepository repository;
//  @Autowired
//  AccountRepository accountRepository;
//  @Autowired
//  CreditCardRepository creditCardRepository;
//  @Autowired
//  PaymentRepository paymentRepository;
  @Autowired
  RoleRepository roleRepository;
//  @Autowired
//  UserRepository userRepository;

  /**
   *findAll method
   *@param
   *@return
  */
  @RequestMapping("/findall")
  public String findAll() {
    StringBuilder result = new StringBuilder();
    for (Role role : roleRepository.findAll()) {
      result.append(role.toString());
      result.append("<br>");
    }
    return result.toString();
  }
}