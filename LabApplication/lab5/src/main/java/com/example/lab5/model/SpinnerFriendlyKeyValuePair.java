package com.example.lab5.model;

import androidx.annotation.NonNull;

public class SpinnerFriendlyKeyValuePair {
    private final String text;
    private final int value;

    public SpinnerFriendlyKeyValuePair(String text, int value) {
        this.value = value;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
