package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.adapters.AccountsAdapter;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.repositories.AccountsRepository;
import com.example.lab6.dialogs.ProcessAccountDialog;

import java.util.ArrayList;

public class AccountsFragment extends Fragment {
    Button addButton;
    GridView accountsList;

    private View self;

    private AccountsRepository repository;

    public AccountsFragment() {
        super(R.layout.fragment_accounts);
    }

    public AccountsFragment(AccountsRepository repository) {
        super(R.layout.fragment_accounts);
        this.repository = repository;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.self = view;
        initializeCreationButton(view);
        fillGridView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public AccountsRepository getRepository() {
        return repository;
    }

    public void updateContent() {
        fillGridView(self);
    }

    private void initializeCreationButton(View view) {
        addButton = view.findViewById(R.id.add_account_button);
        addButton.setOnClickListener(buttonView -> {
            ProcessAccountDialog dialog = new ProcessAccountDialog(this, repository);
            dialog.show(getParentFragmentManager(), "addAccount");
        });
    }

    private void fillGridView(View view) {
        accountsList = view.findViewById(R.id.accounts_list);
        ArrayList<Account> accounts = repository.getAll();
        AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, this);
        accountsList.setAdapter(adapter);
    }
}