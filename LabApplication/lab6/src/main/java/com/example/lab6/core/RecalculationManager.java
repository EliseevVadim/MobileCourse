package com.example.lab6.core;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab6.core.models.Account;
import com.example.lab6.core.repositories.AccountsRepository;
import com.example.lab6.core.repositories.ExpensesRepository;
import com.example.lab6.core.repositories.IncomesRepository;

import java.util.ArrayList;

public class RecalculationManager {
    private final DatabaseManager dbManager;

    public RecalculationManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public double[] recalculateState() {
        double[] result = new double[2];
        double entireSum = 0;
        double entireSavings = 0;
        AccountsRepository accountsRepository = new AccountsRepository(dbManager);
        ExpensesRepository expensesRepository = new ExpensesRepository(dbManager);
        IncomesRepository incomesRepository = new IncomesRepository(dbManager);
        ArrayList<Account> accounts = accountsRepository.getAll();
        for (Account account : accounts) {
            double allIncomes = getIncomesSumForAccount(account.getId());
            double allExpenses = getExpensesSumForAccount(account.getId());
            double allSavings = getSavingsSumForAccount(account.getId());
            double newBalance = account.getBalance() + allIncomes - allExpenses;
            if (newBalance < 0)
                throw new IllegalStateException();
            entireSum += newBalance;
            entireSavings += allSavings;
            account.setBalance(newBalance);
            accountsRepository.update(account);
            incomesRepository.deleteByAccountId(account.getId());
            expensesRepository.deleteByAccountId(account.getId());
        }
        result[0] = entireSum;
        result[1] = entireSavings;
        return result;
    }

    private double getIncomesSumForAccount(int accountId) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT SUM(Turnovers.quantity) AS result " +
            "FROM Turnovers INNER JOIN Incomes ON Turnovers.id = Incomes.turnoverId " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id " +
            "WHERE Accounts.id = ?";
        String[] selectionParams = new String[] {Integer.toString(accountId)};
        Cursor cursor = db.rawQuery(query, selectionParams);
        double sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }

    private double getExpensesSumForAccount(int accountId) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT SUM(Turnovers.quantity) AS result " +
            "FROM Turnovers INNER JOIN Expenses ON Turnovers.id = Expenses.turnoverId " +
            "INNER JOIN Accounts ON Turnovers.accountId = Accounts.id " +
            "WHERE Accounts.id = ?";
        String[] selectionParams = new String[] {Integer.toString(accountId)};
        Cursor cursor = db.rawQuery(query, selectionParams);
        double sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }

    private double getSavingsSumForAccount(int accountId) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        String query = "SELECT SUM(quantity) AS result " +
            "FROM Savings INNER JOIN Accounts ON Savings.accountId = Accounts.id " +
            "WHERE Accounts.id = ?";
        String[] selectionParams = new String[] {Integer.toString(accountId)};
        Cursor cursor = db.rawQuery(query, selectionParams);
        double sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
}
