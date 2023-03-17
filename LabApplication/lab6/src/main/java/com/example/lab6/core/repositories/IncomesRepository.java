package com.example.lab6.core.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.models.Category;
import com.example.lab6.core.models.Income;

import java.util.ArrayList;
import java.util.Date;

public class IncomesRepository implements iRepository<Income> {
    private final DatabaseManager dbManager;

    public IncomesRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Income> getAll() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Turnovers.id, Turnovers.turnoverDate, Turnovers.name, Turnovers.quantity, Turnovers.accountId, Accounts.accountName, " +
            "Incomes.categoryId, IncomeCategories.categoryName " +
            "FROM Incomes " +
            "INNER JOIN Turnovers ON Incomes.turnoverId = Turnovers.id " +
            "INNER JOIN IncomeCategories ON Incomes.categoryId = IncomeCategories.id " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id";
        ArrayList<Income> incomes = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[] {});
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int turnoverDateIndex = cursor.getColumnIndex("turnoverDate");
            int accountIdIndex = cursor.getColumnIndex("accountId");
            int accountNameIndex = cursor.getColumnIndex("accountName");
            int categoryIdIndex = cursor.getColumnIndex("categoryId");
            int categoryNameIndex = cursor.getColumnIndex("categoryName");
            do {
                Income income = new Income(
                    cursor.getInt(idColumnIndex),
                    new Date(cursor.getLong(turnoverDateIndex)),
                    cursor.getString(nameIndex),
                    cursor.getDouble(quantityIndex),
                    cursor.getInt(accountIdIndex),
                    cursor.getInt(categoryIdIndex)
                );
                income.setCategoryName(cursor.getString(categoryNameIndex));
                income.setAccountName(cursor.getString(accountNameIndex));
                incomes.add(income);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return incomes;
    }

    @Override
    public Income getById(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Turnovers.id, Turnovers.turnoverDate, Turnovers.name, Turnovers.quantity, Turnovers.accountId, Accounts.accountName, " +
            "Incomes.categoryId, IncomeCategories.categoryName " +
            "FROM Incomes " +
            "INNER JOIN Turnovers ON Incomes.turnoverId = Turnovers.id " +
            "INNER JOIN IncomeCategories ON Incomes.categoryId = IncomeCategories.id " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id " +
            "WHERE Turnovers.id = ?";
        String[] selectionParams = new String[] {Integer.toString(id)};
        Income income = null;
        Cursor cursor = db.rawQuery(query, selectionParams);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int turnoverDateIndex = cursor.getColumnIndex("turnoverDate");
            int accountIdIndex = cursor.getColumnIndex("accountId");
            int accountNameIndex = cursor.getColumnIndex("accountName");
            int categoryIdIndex = cursor.getColumnIndex("categoryId");
            int categoryNameIndex = cursor.getColumnIndex("categoryName");
            income = new Income(
                cursor.getInt(idColumnIndex),
                new Date(cursor.getLong(turnoverDateIndex)),
                cursor.getString(nameIndex),
                cursor.getDouble(quantityIndex),
                cursor.getInt(accountIdIndex),
                cursor.getInt(categoryIdIndex)
            );
            income.setCategoryName(cursor.getString(categoryNameIndex));
            income.setAccountName(cursor.getString(accountNameIndex));
        }
        cursor.close();
        db.close();
        return income;
    }

    @Override
    public void create(Income element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        contentValues.put("turnoverDate", element.getTurnoverDate().getTime());
        contentValues.put("accountId", element.getAccountId());
        long rowID = db.insert("Turnovers", null, contentValues);
        contentValues.clear();
        contentValues.put("turnoverId", rowID);
        contentValues.put("categoryId", element.getCategoryId());
        db.insert("Incomes", null, contentValues);
        db.close();
    }

    @Override
    public void update(Income element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        contentValues.put("turnoverDate", element.getTurnoverDate().getTime());
        contentValues.put("accountId", element.getAccountId());
        String whereClause = "id = ?";
        String[] updatingParams = new String[] {Integer.toString(element.getId())};
        db.update("Turnovers", contentValues, whereClause, updatingParams);
        contentValues.clear();
        contentValues.put("categoryId", element.getCategoryId());
        db.update("Incomes", contentValues, "turnoverId = ?", updatingParams);
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.delete("Turnovers", "id = ?", new String[] {Integer.toString(id)});
        db.close();
    }

    public void deleteByAccountId(int accountId) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "DELETE " +
            "FROM Turnovers " +
            "WHERE Turnovers.id IN (" +
            "SELECT Turnovers.id FROM Turnovers " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id " +
            "INNER JOIN Incomes ON Incomes.turnoverId = Turnovers.id " +
            "WHERE Turnovers.AccountId = ?" +
            ")";
        String[] deletingParams = new String[] {Integer.toString(accountId)};
        db.execSQL(query, deletingParams);
        db.close();
    }

    public ArrayList<Category> getIncomeCategories() {
        return new CategoriesRepository(dbManager).getIncomeCategories();
    }

    public ArrayList<Account> getAccounts() {
        return new AccountsRepository(dbManager).getAll();
    }
}
