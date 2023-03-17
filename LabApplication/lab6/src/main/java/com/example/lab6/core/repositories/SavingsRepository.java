package com.example.lab6.core.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.models.Saving;

import java.util.ArrayList;

public class SavingsRepository implements iRepository<Saving> {
    private final DatabaseManager dbManager;

    public SavingsRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Saving> getAll() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Savings.id, accountName, name, quantity, accountId " +
            "FROM Savings " +
            "INNER JOIN Accounts ON Savings.AccountId = Accounts.id";
        ArrayList<Saving> savings = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[] {});
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int accountNameColumnIndex = cursor.getColumnIndex("accountName");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int accountIdIndex = cursor.getColumnIndex("accountId");
            do {
                Saving addition = new Saving(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(nameIndex),
                    cursor.getDouble(quantityIndex),
                    cursor.getInt(accountIdIndex)
                );
                addition.setAccountName(cursor.getString(accountNameColumnIndex));
                savings.add(addition);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return savings;
    }

    @Override
    public Saving getById(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT id, accountName, name, quantity, accountId " +
            "FROM Savings " +
            "INNER JOIN Accounts ON Savings.AccountId = Accounts.id " +
            "WHERE Savings.id = ?";
        String[] selectionParams = new String[] {Integer.toString(id)};
        Saving saving = null;
        Cursor cursor = db.rawQuery(query, selectionParams);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int accountNameColumnIndex = cursor.getColumnIndex("accountName");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int accountIdIndex = cursor.getColumnIndex("accountId");
            saving = new Saving(
                cursor.getInt(idColumnIndex),
                cursor.getString(nameIndex),
                cursor.getDouble(quantityIndex),
                cursor.getInt(accountIdIndex)
            );
            saving.setAccountName(cursor.getString(accountNameColumnIndex));
        }
        cursor.close();
        db.close();
        return saving;
    }

    @Override
    public void create(Saving element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        contentValues.put("accountId", element.getAccountId());
        db.insert("Savings", null, contentValues);
        db.close();
    }

    @Override
    public void update(Saving element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        contentValues.put("accountId", element.getAccountId());
        String whereClause = "id = ?";
        String[] updatingParams = new String[] {Integer.toString(element.getId())};
        db.update("Savings", contentValues, whereClause, updatingParams);
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.delete("Savings", "id = ?", new String[] {Integer.toString(id)});
        db.close();
    }

    public ArrayList<Account> getAccounts() {
        return new AccountsRepository(dbManager).getAll();
    }
}
