package com.epam.labproject.web;

import com.epam.labproject.model.Customer;
import com.epam.labproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Autowired
    CustomerRepository repository;

    @RequestMapping("/findall")
    public String findAll() {
        StringBuilder result = new StringBuilder();
        for(Customer customer : repository.findAll()){
            result.append(customer.toString());
            result.append("<br>");
        }
        return result.toString();
    }
}