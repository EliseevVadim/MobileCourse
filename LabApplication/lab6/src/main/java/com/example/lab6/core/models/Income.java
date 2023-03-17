package com.example.lab6.core.models;

import java.util.Date;

public class Income extends Turnover {
    private final int categoryId;
    private String categoryName;
    private String accountName;

    public Income(int id, Date turnoverDate, String name, double quantity, int accountId, int categoryId) {
        this.id = id;
        this.turnoverDate = turnoverDate;
        this.name = name;
        this.quantity = quantity;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public Income(Date turnoverDate, String name, double quantity, int accountId, int categoryId) {
        this.turnoverDate = turnoverDate;
        this.name = name;
        this.quantity = quantity;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
