package com.example.lab5.service;

import android.app.Activity;

import com.example.lab5.model.SpinnerFriendlyKeyValuePair;
import com.example.lab5.model.UserInput;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Reader {
    private final Activity context;

    public Reader(Activity context) {
        this.context = context;
    }

    public SpinnerFriendlyKeyValuePair[] readSpinnerContentFromJson(String fileName) throws IOException {
        Gson gson = new Gson();
        InputStream jsonReader = context.getAssets().open(fileName);
        byte[] buffer = new byte[jsonReader.available()];
        jsonReader.read(buffer);
        jsonReader.close();
        String fileContent = new String(buffer);
        return gson.fromJson(fileContent, SpinnerFriendlyKeyValuePair[].class);
    }

    public UserInput loadUserInput(String fileName) throws IOException {
        Gson gson = new Gson();
        FileInputStream reader = context.openFileInput(fileName);
        byte[] buffer = new byte[reader.available()];
        reader.read(buffer);
        String fileContent = new String(buffer);
        reader.close();
        return gson.fromJson(fileContent, UserInput.class);
    }
}
