package com.example.lab6.core.models;

import androidx.annotation.NonNull;

public class Account {
    private int id;
    private String accountName;
    private double balance;

    public Account(int id, String accountName, double balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }

    public Account(String accountName, double balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @NonNull
    @Override
    public String toString() {
        return accountName;
    }
}
