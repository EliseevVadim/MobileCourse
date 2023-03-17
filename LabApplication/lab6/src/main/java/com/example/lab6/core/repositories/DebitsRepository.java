package com.example.lab6.core.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Debit;

import java.util.ArrayList;

public class DebitsRepository implements iRepository<Debit> {
    private final DatabaseManager dbManager;

    public DebitsRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Debit> getAll() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Obligations.id, Obligations.name, Obligations.quantity " +
            "FROM Debits " +
            "INNER JOIN Obligations ON Debits.obligationId = Obligations.id";
        ArrayList<Debit> debits = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[] {});
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            do {
                Debit addition = new Debit(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(nameIndex),
                    cursor.getDouble(quantityIndex)
                );
                debits.add(addition);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return debits;
    }

    @Override
    public Debit getById(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Obligations.id, Obligations.name, Obligations.quantity " +
            "FROM Debits " +
            "INNER JOIN Obligations ON Debits.obligationId = Obligations.id " +
            "WHERE Obligations.id = ?";
        String[] selectionParams = new String[] {Integer.toString(id)};
        Debit debit = null;
        Cursor cursor = db.rawQuery(query, selectionParams);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            debit = new Debit(
                cursor.getInt(idColumnIndex),
                cursor.getString(nameIndex),
                cursor.getDouble(quantityIndex)
            );
        }
        cursor.close();
        db.close();
        return debit;
    }

    @Override
    public void create(Debit element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        long rowID = db.insert("Obligations", null, contentValues);
        contentValues.clear();
        contentValues.put("obligationId", rowID);
        db.insert("Debits", null, contentValues);
        db.close();
    }

    @Override
    public void update(Debit element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        String whereClause = "id = ?";
        String[] updatingParams = new String[] {Integer.toString(element.getId())};
        db.update("Obligations", contentValues, whereClause, updatingParams);
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.delete("Obligations", "id = ?", new String[] {Integer.toString(id)});
        db.close();
    }
}
