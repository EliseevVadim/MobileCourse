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

import com.example.lab6.IncomesFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.models.Category;
import com.example.lab6.core.models.Income;
import com.example.lab6.core.repositories.IncomesRepository;

import java.util.ArrayList;
import java.util.Date;

public class ProcessIncomeDialog extends DialogFragment {
    EditText incomeNameInput;
    EditText incomeQuantityInput;
    DatePicker incomeDateInput;
    Spinner incomeAccountIdInput;
    Spinner incomeCategoryIdInput;

    private Income updatingIncome;
    private final IncomesRepository repository;
    private final IncomesFragment parent;

    public ProcessIncomeDialog(IncomesFragment parent, IncomesRepository repository) {
        super();
        this.parent = parent;
        this.repository = repository;
    }

    public ProcessIncomeDialog(IncomesFragment parent, IncomesRepository repository, Income income) {
        super();
        this.parent = parent;
        this.updatingIncome = income;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.income_processor, null));
        incomeNameInput = view.findViewById(R.id.income_name_input);
        incomeQuantityInput = view.findViewById(R.id.income_quantity_input);
        incomeDateInput = view.findViewById(R.id.income_date_input);
        incomeAccountIdInput = view.findViewById(R.id.income_accountId_selector);
        ArrayList<Account> accounts = repository.getAccounts();
        ArrayAdapter<Account> accountsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, accounts);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeAccountIdInput.setAdapter(accountsAdapter);
        incomeCategoryIdInput = view.findViewById(R.id.income_categoryId_selector);
        ArrayList<Category> incomeCategories = repository.getIncomeCategories();
        ArrayAdapter<Category> incomeCategoriesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, incomeCategories);
        incomeCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeCategoryIdInput.setAdapter(incomeCategoriesAdapter);
        if (updatingIncome != null) {
            incomeNameInput.setText(updatingIncome.getName());
            incomeQuantityInput.setText(Double.toString(updatingIncome.getQuantity()));
            incomeDateInput.init(
                updatingIncome.getTurnoverDate().getYear(),
                updatingIncome.getTurnoverDate().getMonth(),
                updatingIncome.getTurnoverDate().getDate(),
                null
            );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                incomeAccountIdInput.setSelection(accountsAdapter.getPosition(accounts
                    .stream()
                    .filter(account -> account.getId() == updatingIncome.getAccountId())
                    .findAny()
                    .get()));
                incomeCategoryIdInput.setSelection(incomeCategoriesAdapter.getPosition(incomeCategories
                    .stream()
                    .filter(account -> account.getId() == updatingIncome.getCategoryId())
                    .findAny()
                    .get()));
            }
        }
        builder.setView(view)
            .setTitle(updatingIncome == null ? "Создать запись о доходе" : "Изменить запись о доходе")
            .setPositiveButton(updatingIncome == null ? R.string.add_button_text : R.string.update_button_text, (dialogInterface, i) -> {
                if (updatingIncome == null)
                    createIncome();
                else
                    updateIncome();
                parent.updateContent();
                dismiss();
            })
            .setNegativeButton(R.string.disgard_button_text, (dialogInterface, i) -> {
                dismiss();
            });
        return builder.create();
    }

    private void createIncome() {
        try {
            String incomeName = incomeNameInput.getText().toString();
            double quantity = Double.parseDouble(incomeQuantityInput.getText().toString());
            Date incomeDate = new Date(
                incomeDateInput.getYear(),
                incomeDateInput.getMonth(),
                incomeDateInput.getDayOfMonth()
            );
            int accountId = ((Account)incomeAccountIdInput.getSelectedItem()).getId();
            int categoryId = ((Category)incomeCategoryIdInput.getSelectedItem()).getId();
            Income income = new Income(incomeDate, incomeName, quantity, accountId, categoryId);
            repository.create(income);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void updateIncome() {
        try {
            String incomeName = incomeNameInput.getText().toString();
            double quantity = Double.parseDouble(incomeQuantityInput.getText().toString());
            Date incomeDate = new Date(
                incomeDateInput.getYear(),
                incomeDateInput.getMonth(),
                incomeDateInput.getDayOfMonth()
            );
            int accountId = ((Account)incomeAccountIdInput.getSelectedItem()).getId();
            int categoryId = ((Category)incomeCategoryIdInput.getSelectedItem()).getId();
            Income income = new Income(updatingIncome.getId(), incomeDate, incomeName, quantity, accountId, categoryId);
            repository.update(income);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }
}
