package com.example.lab5.service;

import android.app.Activity;
import android.content.Context;

import com.example.lab5.model.UserInput;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {
    private final Activity context;

    public Writer(Activity context) {
        this.context = context;
    }

    public void saveUserInputToFile(UserInput userInput, String fileName) throws IOException {
        Gson gson = new Gson();
        String text = gson.toJson(userInput);
        FileOutputStream writer = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        writer.write(text.getBytes());
        writer.close();
    }
}
