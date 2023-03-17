package com.example.lab6.core.models;

import java.util.Date;

public class Loan extends Obligation {
    private final Date deadLine;

    public Loan(int id, String name, double quantity, Date deadLine) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.deadLine = deadLine;
    }

    public Loan(String name, double quantity, Date deadLine) {
        this.name = name;
        this.quantity = quantity;
        this.deadLine = deadLine;
    }

    public Date getDeadLine() {
        return deadLine;
    }
}
