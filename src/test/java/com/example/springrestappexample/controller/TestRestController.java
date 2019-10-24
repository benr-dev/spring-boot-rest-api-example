package com.example.springrestappexample.controller;

import com.example.springrestappexample.service.Account;
import com.example.springrestappexample.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// This test suite demonstrates injection of a mocking Configuration
// using TestConfiguration & ActiveProfile annotation. To accomplish this,
// it is necessary to mark the non-test configuration with
// Profile("default") otherwise Spring complains about multiple bean
// definitions.
// Note that Spring is being used to inject only the config here -
// the tests are plain Junit/Hamcrest/Mockito unit tests.

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TestRestController {

    @TestConfiguration
    static class Config {
        @Bean
        public AccountService accountService() {
            return mock(AccountService.class);
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountService accountService;

    private ArrayList<Account> testAccountCollection;

    @BeforeEach
    public void setUp() {
        testAccountCollection = new ArrayList<>();

        Account account1 = new Account("Current Account",123456789L, 987L);
        testAccountCollection.add(account1);

        reset(accountService);
    }

    @Test
    public void when_getAllAccounts_then_getAllAccountsInvoked() throws Exception {
        // given
        when(accountService.getAllAccounts()).thenReturn(testAccountCollection);

        // when
        ResultActions response = mvc.perform(
                MockMvcRequestBuilders
                    .get("/accounts")
                    .accept(MediaType.APPLICATION_JSON));

        // then
        response.andExpect(status().isOk());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    public void when_getAllAccountsAndAccountsReturnedFromService_then_getAllAccountsReturnsExpectedJson() throws Exception {
        // given
        when(accountService.getAllAccounts()).thenReturn(testAccountCollection);

        // when
        ResultActions response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/accounts")
                        .accept(MediaType.APPLICATION_JSON));

        // then
        response
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType("application/json")))
                .andExpect(jsonPath("$[0].name", is("Current Account")))
                .andExpect(jsonPath("$[0].number", is(123456789L), Long.class))
                .andExpect(jsonPath("$[0].balance", is(987L), Long.class));
    }
}
