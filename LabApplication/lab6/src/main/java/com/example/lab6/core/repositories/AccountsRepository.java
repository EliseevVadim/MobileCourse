package com.example.lab6.core.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Account;

import java.util.ArrayList;

public class AccountsRepository implements iRepository<Account> {
    private final DatabaseManager dbManager;

    public AccountsRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Account> getAll() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ArrayList<Account> accounts = new ArrayList<>();
        Cursor cursor = db.query("Accounts", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int accountNameColumnIndex = cursor.getColumnIndex("accountName");
            int balanceIndex = cursor.getColumnIndex("balance");
            do {
                accounts.add(new Account(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(accountNameColumnIndex),
                    cursor.getInt(balanceIndex)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accounts;
    }

    @Override
    public Account getById(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionParams = new String[] {Integer.toString(id)};
        Account account = null;
        Cursor cursor = db.query("Accounts", null, selection, selectionParams, null, null, null);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int accountNameColumnIndex = cursor.getColumnIndex("accountName");
            int balanceIndex = cursor.getColumnIndex("balance");
            account = new Account(
                cursor.getInt(idColumnIndex),
                cursor.getString(accountNameColumnIndex),
                cursor.getInt(balanceIndex)
            );
        }
        cursor.close();
        db.close();
        return account;
    }

    @Override
    public void create(Account element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountName", element.getAccountName());
        contentValues.put("balance", element.getBalance());
        db.insert("Accounts", null, contentValues);
        db.close();
    }

    @Override
    public void update(Account element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountName", element.getAccountName());
        contentValues.put("balance", element.getBalance());
        String whereClause = "id = ?";
        String[] updatingParams = new String[] {Integer.toString(element.getId())};
        db.update("Accounts", contentValues, whereClause, updatingParams);
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.delete("Accounts", "id = ?", new String[] {Integer.toString(id)});
        db.close();
    }
}
