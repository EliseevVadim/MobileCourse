package com.example.lab6.core.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.models.Category;
import com.example.lab6.core.models.Expense;

import java.util.ArrayList;
import java.util.Date;

public class ExpensesRepository implements iRepository<Expense> {
    private final DatabaseManager dbManager;

    public ExpensesRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Expense> getAll() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Turnovers.id, Turnovers.turnoverDate, Turnovers.name, Turnovers.quantity, Turnovers.accountId, Accounts.accountName, " +
            "Expenses.categoryId, ExpenseCategories.categoryName " +
            "FROM Expenses " +
            "INNER JOIN Turnovers ON Expenses.turnoverId = Turnovers.id " +
            "INNER JOIN ExpenseCategories ON Expenses.categoryId = ExpenseCategories.id " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id";
        ArrayList<Expense> expenses = new ArrayList<>();
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
                Expense expense = new Expense(
                    cursor.getInt(idColumnIndex),
                    new Date(cursor.getLong(turnoverDateIndex)),
                    cursor.getString(nameIndex),
                    cursor.getDouble(quantityIndex),
                    cursor.getInt(accountIdIndex),
                    cursor.getInt(categoryIdIndex)
                );
                expense.setCategoryName(cursor.getString(categoryNameIndex));
                expense.setAccountName(cursor.getString(accountNameIndex));
                expenses.add(expense);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenses;
    }

    @Override
    public Expense getById(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Turnovers.id, Turnovers.turnoverDate, Turnovers.name, Turnovers.quantity, Turnovers.accountId, Accounts.accountName, " +
            "Expenses.categoryId, ExpenseCategories.categoryName " +
            "FROM Expenses " +
            "INNER JOIN Turnovers ON Expenses.turnoverId = Turnovers.id " +
            "INNER JOIN ExpenseCategories ON Expenses.categoryId = ExpenseCategories.id " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id " +
            "WHERE Turnovers.id = ?";
        String[] selectionParams = new String[] {Integer.toString(id)};
        Expense expense = null;
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
            expense = new Expense(
                cursor.getInt(idColumnIndex),
                new Date(cursor.getLong(turnoverDateIndex)),
                cursor.getString(nameIndex),
                cursor.getDouble(quantityIndex),
                cursor.getInt(accountIdIndex),
                cursor.getInt(categoryIdIndex)
            );
            expense.setCategoryName(cursor.getString(categoryNameIndex));
            expense.setAccountName(cursor.getString(accountNameIndex));
        }
        cursor.close();
        db.close();
        return expense;
    }

    @Override
    public void create(Expense element) {
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
        db.insert("Expenses", null, contentValues);
        db.close();
    }

    @Override
    public void update(Expense element) {
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
        db.update("Expenses", contentValues, "turnoverId = ?", updatingParams);
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
            "INNER JOIN Expenses ON Expenses.turnoverId = Turnovers.id " +
            "WHERE Turnovers.AccountId = ?" +
            ")";
        String[] deletingParams = new String[] {Integer.toString(accountId)};
        db.execSQL(query, deletingParams);
        db.close();
    }

    public ArrayList<Category> getExpenseCategories() {
        return new CategoriesRepository(dbManager).getExpenseCategories();
    }

    public ArrayList<Account> getAccounts() {
        return new AccountsRepository(dbManager).getAll();
    }
}
