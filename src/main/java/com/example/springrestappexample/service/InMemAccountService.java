package com.example.springrestappexample.service;

import java.util.Collection;
import java.util.Collections;

public class InMemAccountService implements AccountService {
    public Collection<Account> getAllAccounts() {
        return Collections.emptyList();
    }
}
