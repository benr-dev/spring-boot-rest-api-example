package com.example.springrestappexample.controller;

import com.example.springrestappexample.service.Account;
import com.example.springrestappexample.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = "application/json")
    public Collection<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
