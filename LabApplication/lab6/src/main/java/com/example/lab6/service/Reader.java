package com.example.lab6.service;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Reader {
    private final Context context;

    public Reader(Context context) {
        this.context = context;
    }

    public String readSqlFromFile(String fileName) throws IOException {
        InputStream reader = context.getAssets().open(fileName);
        byte[] buffer = new byte[reader.available()];
        reader.read(buffer);
        reader.close();
        return new String(buffer);
    }
}
