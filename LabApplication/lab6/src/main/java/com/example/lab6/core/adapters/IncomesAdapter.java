package com.example.lab6.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab6.IncomesFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Income;
import com.example.lab6.dialogs.ProcessIncomeDialog;

import java.util.ArrayList;

public class IncomesAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Income> incomes;
    private final IncomesFragment parent;

    public IncomesAdapter(Context context, ArrayList<Income> incomes, IncomesFragment parent) {
        this.context = context;
        this.incomes = incomes;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return incomes.size();
    }

    @Override
    public Object getItem(int i) {
        return incomes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.income_presenter, null);
        TextView idField = view.findViewById(R.id.id_field);
        TextView incomeNameField = view.findViewById(R.id.income_name_field);
        TextView quantityField = view.findViewById(R.id.income_quantity_field);
        TextView dateField = view.findViewById(R.id.income_date_field);
        TextView accountField = view.findViewById(R.id.income_accountName_field);
        TextView categoryField = view.findViewById(R.id.income_categoryName_field);
        Button updateButton = view.findViewById(R.id.updateIncomeButton);
        Button deleteButton = view.findViewById(R.id.deleteIncomeButton);
        Income income = incomes.get(i);
        idField.setText(Integer.toString(income.getId()));
        incomeNameField.setText(income.getName());
        quantityField.setText(String.format("%1$,.2f", income.getQuantity()));
        dateField.setText(String.format("%02d.%02d.%d",
            income.getTurnoverDate().getDate(),
            income.getTurnoverDate().getMonth() + 1,
            income.getTurnoverDate().getYear()));
        accountField.setText(income.getAccountName());
        categoryField.setText(income.getCategoryName());
        updateButton.setOnClickListener(updateButtonView -> {
            ProcessIncomeDialog dialog = new ProcessIncomeDialog(parent, parent.getRepository(), income);
            dialog.show(parent.getParentFragmentManager(), "updateIncome");
        });
        deleteButton.setOnClickListener(deleteButtonView -> {
            parent.getRepository().delete(income.getId());
            parent.updateContent();
        });
        return view;
    }
}
