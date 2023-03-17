package com.example.lab6.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab6.AccountsFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Account;
import com.example.lab6.dialogs.ProcessAccountDialog;

import java.util.ArrayList;

public class AccountsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Account> accounts;
    private final AccountsFragment parent;

    public AccountsAdapter(Context context, ArrayList<Account> accounts, AccountsFragment parent) {
        this.context = context;
        this.accounts = accounts;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int i) {
        return accounts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.account_presenter, null);
        TextView idField = view.findViewById(R.id.id_field);
        TextView accountNameField = view.findViewById(R.id.account_name_field);
        TextView balanceField = view.findViewById(R.id.balance_field);
        Button updateButton = view.findViewById(R.id.updateAccountButton);
        Button deleteButton = view.findViewById(R.id.deleteAccountButton);
        Account account = accounts.get(i);
        idField.setText(Integer.toString(account.getId()));
        accountNameField.setText(account.getAccountName());
        balanceField.setText(String.format("%1$,.2f", account.getBalance()));
        updateButton.setOnClickListener(updateButtonView -> {
            ProcessAccountDialog dialog = new ProcessAccountDialog(parent, parent.getRepository(), account);
            dialog.show(parent.getParentFragmentManager(), "updateAccount");
        });
        deleteButton.setOnClickListener(deleteButtonView -> {
            parent.getRepository().delete(account.getId());
            parent.updateContent();
        });
        return view;
    }
}
