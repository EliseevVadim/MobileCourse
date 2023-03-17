package com.example.lab6.core.models;

import androidx.annotation.NonNull;

public class Category {
    private final int id;
    private final String categoryName;

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return categoryName;
    }
}
