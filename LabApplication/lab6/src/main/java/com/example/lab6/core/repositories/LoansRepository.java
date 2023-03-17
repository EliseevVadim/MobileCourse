package com.example.lab6.core.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.models.Loan;

import java.util.ArrayList;
import java.util.Date;

public class LoansRepository implements iRepository<Loan> {
    private final DatabaseManager dbManager;

    public LoansRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public ArrayList<Loan> getAll() {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Obligations.id, Obligations.name, Obligations.quantity, deadLine " +
            "FROM Loans " +
            "INNER JOIN Obligations ON Loans.obligationId = Obligations.id";
        ArrayList<Loan> loans = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[] {});
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int deadLineIndex = cursor.getColumnIndex("deadLine");
            do {
                Loan addition = new Loan(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(nameIndex),
                    cursor.getDouble(quantityIndex),
                    new Date(cursor.getLong(deadLineIndex))
                );
                loans.add(addition);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return loans;
    }

    @Override
    public Loan getById(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT Obligations.id, Obligations.name, Obligations.quantity, deadLine " +
            "FROM Loans " +
            "INNER JOIN Obligations ON Loans.obligationId = Obligations.id " +
            "WHERE Obligations.id = ?";
        String[] selectionParams = new String[] {Integer.toString(id)};
        Loan loan = null;
        Cursor cursor = db.rawQuery(query, selectionParams);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int deadLineIndex = cursor.getColumnIndex("deadLine");
            loan = new Loan(
                cursor.getInt(idColumnIndex),
                cursor.getString(nameIndex),
                cursor.getDouble(quantityIndex),
                new Date(cursor.getLong(deadLineIndex))
            );
        }
        cursor.close();
        db.close();
        return loan;
    }

    @Override
    public void create(Loan element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        long rowID = db.insert("Obligations", null, contentValues);
        contentValues.clear();
        contentValues.put("obligationId", rowID);
        contentValues.put("deadLine", element.getDeadLine().getTime());
        db.insert("Loans", null, contentValues);
        db.close();
    }

    @Override
    public void update(Loan element) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", element.getName());
        contentValues.put("quantity", element.getQuantity());
        String whereClause = "id = ?";
        String[] updatingParams = new String[] {Integer.toString(element.getId())};
        db.update("Obligations", contentValues, whereClause, updatingParams);
        contentValues.clear();
        contentValues.put("deadLine", element.getDeadLine().getTime());
        db.update("Loans", contentValues, "obligationId = ?", updatingParams);
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.delete("Obligations", "id = ?", new String[] {Integer.toString(id)});
        db.close();
    }
}
