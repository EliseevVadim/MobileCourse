package com.example.lab6.core.repositories;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Category;

import java.util.ArrayList;

public class CategoriesRepository implements iRepository<Category> {
    private final DatabaseManager dbManager;

    public CategoriesRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Category> getAll() {
        return null;
    }

    @Override
    public Category getById(int id) {
        return null;
    }

    @Override
    public void create(Category element) {

    }

    @Override
    public void update(Category element) {

    }

    @Override
    public void delete(int id) {

    }

    public ArrayList<Category> getIncomeCategories() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = db.query("IncomeCategories", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int categoryNameColumnIndex = cursor.getColumnIndex("categoryName");
            do {
                categories.add(new Category(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(categoryNameColumnIndex)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public ArrayList<Category> getExpenseCategories() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = db.query("ExpenseCategories", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int categoryNameColumnIndex = cursor.getColumnIndex("categoryName");
            do {
                categories.add(new Category(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(categoryNameColumnIndex)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }
}
