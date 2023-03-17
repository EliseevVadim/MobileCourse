package com.example.lab6.core.models;

public class Saving {
    private int id;
    private String name;
    private double quantity;
    private int accountId;
    private String accountName;

    public Saving(int id, String name, double quantity, int accountId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.accountId = accountId;
    }

    public Saving(String name, double quantity, int accountId) {
        this.name = name;
        this.quantity = quantity;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
