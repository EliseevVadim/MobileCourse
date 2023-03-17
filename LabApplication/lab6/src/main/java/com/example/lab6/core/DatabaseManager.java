package com.example.lab6.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lab6.service.Reader;

import java.io.IOException;

public class DatabaseManager extends SQLiteOpenHelper {
    private final Reader reader;

    public DatabaseManager(Context context) {
        super(context, "accountingDatabase", null, 1);
        reader = new Reader(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String sqlStatement = reader.readSqlFromFile("migration.sql");
            String[] queries = sqlStatement.split("//");
            for (String query : queries) {
                sqLiteDatabase.execSQL(query);
            }
            Log.d("Runtime log", "Database was successfully migrated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
