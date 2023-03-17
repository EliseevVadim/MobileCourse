package com.example.lab6.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab6.ExpensesFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Expense;
import com.example.lab6.dialogs.ProcessExpenseDialog;

import java.util.ArrayList;

public class ExpensesAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Expense> expenses;
    private final ExpensesFragment parent;

    public ExpensesAdapter(Context context, ArrayList<Expense> incomes, ExpensesFragment parent) {
        this.context = context;
        this.expenses = incomes;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int i) {
        return expenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.expense_presenter, null);
        TextView idField = view.findViewById(R.id.id_field);
        TextView expenseNameField = view.findViewById(R.id.expense_name_field);
        TextView quantityField = view.findViewById(R.id.expense_quantity_field);
        TextView dateField = view.findViewById(R.id.expense_date_field);
        TextView accountField = view.findViewById(R.id.expense_accountName_field);
        TextView categoryField = view.findViewById(R.id.expense_categoryName_field);
        Button updateButton = view.findViewById(R.id.updateExpenseButton);
        Button deleteButton = view.findViewById(R.id.deleteExpenseButton);
        Expense expense = expenses.get(i);
        idField.setText(Integer.toString(expense.getId()));
        expenseNameField.setText(expense.getName());
        quantityField.setText(String.format("%1$,.2f", expense.getQuantity()));
        dateField.setText(String.format("%02d.%02d.%d",
            expense.getTurnoverDate().getDate(),
            expense.getTurnoverDate().getMonth() + 1,
            expense.getTurnoverDate().getYear()));
        accountField.setText(expense.getAccountName());
        categoryField.setText(expense.getCategoryName());
        updateButton.setOnClickListener(updateButtonView -> {
            ProcessExpenseDialog dialog = new ProcessExpenseDialog(parent, parent.getRepository(), expense);
            dialog.show(parent.getParentFragmentManager(), "updateExpense");
        });
        deleteButton.setOnClickListener(deleteButtonView -> {
            parent.getRepository().delete(expense.getId());
            parent.updateContent();
        });
        return view;
    }
}
