package com.example.lab6.core.models;

public class Debit extends Obligation {
    public Debit(int id, String name, double quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Debit(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
