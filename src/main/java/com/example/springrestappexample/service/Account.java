package com.example.springrestappexample.service;

public class Account {
    private final String name;
    private final long number;
    private final long balance;

    public Account(String name, long number, long balance) {
        this.name = name;
        this.number = number;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }

    public long getBalance() {
        return balance;
    }
}
