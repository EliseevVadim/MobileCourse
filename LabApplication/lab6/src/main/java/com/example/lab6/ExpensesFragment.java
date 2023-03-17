package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.adapters.ExpensesAdapter;
import com.example.lab6.core.models.Expense;
import com.example.lab6.core.repositories.ExpensesRepository;
import com.example.lab6.dialogs.ProcessExpenseDialog;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {
    Button addButton;
    GridView expensesList;

    private View self;

    private ExpensesRepository repository;

    public ExpensesFragment() {
        super(R.layout.fragment_expenses);
    }

    public ExpensesFragment(ExpensesRepository repository) {
        super(R.layout.fragment_expenses);
        this.repository = repository;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.self = view;
        initializeCreationButton(view);
        fillGridView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public ExpensesRepository getRepository() {
        return repository;
    }

    public void updateContent() {
        fillGridView(self);
    }

    private void initializeCreationButton(View view) {
        addButton = view.findViewById(R.id.add_expense_button);
        addButton.setOnClickListener(buttonView -> {
            ProcessExpenseDialog dialog = new ProcessExpenseDialog(this, repository);
            dialog.show(getParentFragmentManager(), "addExpense");
        });
    }

    private void fillGridView(View view) {
        expensesList = view.findViewById(R.id.expenses_list);
        ArrayList<Expense> expenses = repository.getAll();
        ExpensesAdapter adapter = new ExpensesAdapter(getContext(), expenses, this);
        expensesList.setAdapter(adapter);
    }
}