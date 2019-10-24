package com.example.springrestappexample.controller;

import com.example.springrestappexample.service.Account;
import com.example.springrestappexample.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;

// This test suite demonstrates injection of a mocking Configuration
// using TestConfiguration & ActiveProfile annotation. To accomplish this,
// it is necessary to mark the non-test configuration with
// Profile("default") otherwise Spring complains about multiple bean
// definitions.
// Note that Spring is being used to inject only the config here -
// the tests are plain Junit/Hamcrest/Mockito unit tests.

@ActiveProfiles("test")
@SpringBootTest
public class TestAccountController {


    @TestConfiguration
    static class Config {
        @Bean
        public AccountService accountService() {
            return mock(AccountService.class);
        }
    }

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountService accountService;

    private ArrayList<Account> testAccountCollection;

    @BeforeEach
    public void setUp() {
        testAccountCollection = new ArrayList<>();

        Account account1 = new Account("Current Account", 123456789L, 123L);
        testAccountCollection.add(account1);
    }

    @Test
    public void when_getAllAccounts_then_getAllAccountsInvoked() {
        // when
        accountController.getAllAccounts();

        // then
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    public void when_getAllAccountsWithAccountsReturned_then_expectedAccountsReturned() {
        // given
        when(accountService.getAllAccounts()).thenReturn(testAccountCollection);

        // when
        Collection<Account> accounts = accountController.getAllAccounts();

        // then
        assertThat(accounts, hasSize(testAccountCollection.size()));

        Account account = accounts.iterator().next();
        assertThat(account.getName(), is("Current Account"));
        assertThat(account.getNumber(), is(123456789L));
        assertThat(account.getBalance(), is(123L));
    }
}
