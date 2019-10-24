package com.example.springrestappexample.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

// Testing a service implementation. No need for any
// Spring-related shenanigans here since the service layer
// is pure business layer POJOs.
public class TestInMemAccountService {

    private InMemAccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = new InMemAccountService();
    }

    @Test
    public void when_getAllAccountsAndNoAccounts_then_emptyCollectionReturned() {
        assertThat(accountService.getAllAccounts(), hasSize(0));
    }
}
