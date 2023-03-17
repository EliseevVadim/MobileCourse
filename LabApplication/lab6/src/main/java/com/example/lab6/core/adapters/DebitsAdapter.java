package com.example.lab6.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab6.DebitsFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Debit;
import com.example.lab6.dialogs.ProcessDebitDialog;

import java.util.ArrayList;

public class DebitsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Debit> debits;
    private final DebitsFragment parent;

    public DebitsAdapter(Context context, ArrayList<Debit> debits, DebitsFragment parent) {
        this.context = context;
        this.debits = debits;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return debits.size();
    }

    @Override
    public Object getItem(int i) {
        return debits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.debit_presenter, null);
        TextView idField = view.findViewById(R.id.id_field);
        TextView debitNameField = view.findViewById(R.id.debit_name_field);
        TextView quantityField = view.findViewById(R.id.debit_quantity_field);
        Button updateButton = view.findViewById(R.id.updateDebitButton);
        Button deleteButton = view.findViewById(R.id.deleteDebitButton);
        Debit debit = debits.get(i);
        idField.setText(Integer.toString(debit.getId()));
        debitNameField.setText(debit.getName());
        quantityField.setText(String.format("%1$,.2f", debit.getQuantity()));
        updateButton.setOnClickListener(updateButtonView -> {
            ProcessDebitDialog dialog = new ProcessDebitDialog(parent, parent.getRepository(), debit);
            dialog.show(parent.getParentFragmentManager(), "updateDebit");
        });
        deleteButton.setOnClickListener(deleteButtonView -> {
            parent.getRepository().delete(debit.getId());
            parent.updateContent();
        });
        return view;
    }
}
