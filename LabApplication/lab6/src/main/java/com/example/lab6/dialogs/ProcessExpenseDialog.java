package com.example.lab6.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lab6.ExpensesFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.models.Category;
import com.example.lab6.core.models.Expense;
import com.example.lab6.core.repositories.ExpensesRepository;

import java.util.ArrayList;
import java.util.Date;

public class ProcessExpenseDialog extends DialogFragment {
    EditText expenseNameInput;
    EditText expenseQuantityInput;
    DatePicker expenseDateInput;
    Spinner expenseAccountIdInput;
    Spinner expenseCategoryIdInput;

    private Expense updatingExpense;
    private final ExpensesRepository repository;
    private final ExpensesFragment parent;

    public ProcessExpenseDialog(ExpensesFragment parent, ExpensesRepository repository) {
        super();
        this.parent = parent;
        this.repository = repository;
    }

    public ProcessExpenseDialog(ExpensesFragment parent, ExpensesRepository repository, Expense expense) {
        super();
        this.parent = parent;
        this.updatingExpense = expense;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.expense_processor, null));
        expenseNameInput = view.findViewById(R.id.expense_name_input);
        expenseQuantityInput = view.findViewById(R.id.expense_quantity_input);
        expenseDateInput = view.findViewById(R.id.expense_date_input);
        expenseAccountIdInput = view.findViewById(R.id.expense_accountId_selector);
        ArrayList<Account> accounts = repository.getAccounts();
        ArrayAdapter<Account> accountsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, accounts);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseAccountIdInput.setAdapter(accountsAdapter);
        expenseCategoryIdInput = view.findViewById(R.id.expense_categoryId_selector);
        ArrayList<Category> expenseCategories = repository.getExpenseCategories();
        ArrayAdapter<Category> expenseCategoriesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, expenseCategories);
        expenseCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategoryIdInput.setAdapter(expenseCategoriesAdapter);
        if (updatingExpense != null) {
            expenseNameInput.setText(updatingExpense.getName());
            expenseQuantityInput.setText(Double.toString(updatingExpense.getQuantity()));
            expenseDateInput.init(
                updatingExpense.getTurnoverDate().getYear(),
                updatingExpense.getTurnoverDate().getMonth(),
                updatingExpense.getTurnoverDate().getDate(),
                null
            );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                expenseAccountIdInput.setSelection(accountsAdapter.getPosition(accounts
                    .stream()
                    .filter(account -> account.getId() == updatingExpense.getAccountId())
                    .findAny()
                    .get()));
                expenseCategoryIdInput.setSelection(expenseCategoriesAdapter.getPosition(expenseCategories
                    .stream()
                    .filter(account -> account.getId() == updatingExpense.getCategoryId())
                    .findAny()
                    .get()));
            }
        }
        builder.setView(view)
            .setTitle(updatingExpense == null ? "Создать запись о расходе" : "Изменить запись о расходе")
            .setPositiveButton(updatingExpense == null ? R.string.add_button_text : R.string.update_button_text, (dialogInterface, i) -> {
                if (updatingExpense == null)
                    createExpense();
                else
                    updateExpense();
                parent.updateContent();
                dismiss();
            })
            .setNegativeButton(R.string.disgard_button_text, (dialogInterface, i) -> {
                dismiss();
            });
        return builder.create();
    }

    private void createExpense() {
        try {
            String expenseName = expenseNameInput.getText().toString();
            double quantity = Double.parseDouble(expenseQuantityInput.getText().toString());
            Date expenseDate = new Date(
                expenseDateInput.getYear(),
                expenseDateInput.getMonth(),
                expenseDateInput.getDayOfMonth()
            );
            int accountId = ((Account) expenseAccountIdInput.getSelectedItem()).getId();
            int categoryId = ((Category) expenseCategoryIdInput.getSelectedItem()).getId();
            Expense expense = new Expense(expenseDate, expenseName, quantity, accountId, categoryId);
            repository.create(expense);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void updateExpense() {
        try {
            String expenseName = expenseNameInput.getText().toString();
            double quantity = Double.parseDouble(expenseQuantityInput.getText().toString());
            Date expenseDate = new Date(
                expenseDateInput.getYear(),
                expenseDateInput.getMonth(),
                expenseDateInput.getDayOfMonth()
            );
            int accountId = ((Account) expenseAccountIdInput.getSelectedItem()).getId();
            int categoryId = ((Category) expenseCategoryIdInput.getSelectedItem()).getId();
            Expense expense = new Expense(updatingExpense.getId(), expenseDate, expenseName, quantity, accountId, categoryId);
            repository.update(expense);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }
}
