package com.example.lab6.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab6.R;
import com.example.lab6.SavingsFragment;
import com.example.lab6.core.models.Saving;
import com.example.lab6.dialogs.ProcessSavingDialog;

import java.util.ArrayList;

public class SavingsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Saving> savings;
    private final SavingsFragment parent;

    public SavingsAdapter(Context context, ArrayList<Saving> savings, SavingsFragment parent) {
        this.context = context;
        this.savings = savings;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return savings.size();
    }

    @Override
    public Object getItem(int i) {
        return savings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.saving_presenter, null);
        TextView idField = view.findViewById(R.id.saving_id_field);
        TextView accountNameField = view.findViewById(R.id.saving_account_name_field);
        TextView savingNameField = view.findViewById(R.id.saving_name_field);
        TextView balanceField = view.findViewById(R.id.saving_quantity_field);
        Button updateButton = view.findViewById(R.id.updateSavingButton);
        Button deleteButton = view.findViewById(R.id.deleteSavingButton);
        Saving saving = savings.get(i);
        idField.setText(Integer.toString(saving.getId()));
        accountNameField.setText(saving.getAccountName());
        savingNameField.setText(saving.getName());
        balanceField.setText(String.format("%1$,.2f", saving.getQuantity()));
        updateButton.setOnClickListener(updateButtonView -> {
            ProcessSavingDialog dialog = new ProcessSavingDialog(parent, parent.getRepository(), saving);
            dialog.show(parent.getParentFragmentManager(), "updateSaving");
        });
        deleteButton.setOnClickListener(deleteButtonView -> {
            parent.getRepository().delete(saving.getId());
            parent.updateContent();
        });
        return view;
    }
}
