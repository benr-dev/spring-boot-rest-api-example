package com.example.springrestappexample.config;

import com.example.springrestappexample.service.AccountService;
import com.example.springrestappexample.service.InMemAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("default")
@Configuration
public class AccountConfiguration {

    @Bean
    public AccountService accountService() {
        return new InMemAccountService();
    }
}
