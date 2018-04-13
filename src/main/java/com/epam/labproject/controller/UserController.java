package com.epam.labproject.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class UserController {

    private com.epam.labproject.Service.AccountService accountService;
    @Autowired
    public UserController(com.epam.labproject.Service.AccountService accountService){
        this.accountService=accountService;
    }
}
