package com.example.lab6.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lab6.AccountsFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.repositories.AccountsRepository;

public class ProcessAccountDialog extends DialogFragment {
    EditText accountNameInput;
    EditText balanceInput;

    private Account updatingAccount;
    private final AccountsRepository repository;
    private final AccountsFragment parent;

    public ProcessAccountDialog(AccountsFragment parent, AccountsRepository repository) {
        super();
        this.parent = parent;
        this.repository = repository;
    }

    public ProcessAccountDialog(AccountsFragment parent, AccountsRepository repository, Account account) {
        super();
        this.parent = parent;
        this.updatingAccount = account;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.account_processor, null));
        accountNameInput = view.findViewById(R.id.account_name_input);
        balanceInput = view.findViewById(R.id.balance_input);
        if (updatingAccount != null) {
            accountNameInput.setText(updatingAccount.getAccountName());
            balanceInput.setText(Double.toString(updatingAccount.getBalance()));
        }
        builder.setView(view)
            .setTitle(updatingAccount == null ? "Создать счет" : "Изменить счет")
            .setPositiveButton(updatingAccount == null ? R.string.add_button_text : R.string.update_button_text, (dialogInterface, i) -> {
                if (updatingAccount == null)
                    createAccount();
                else
                    updateAccount();
                parent.updateContent();
                dismiss();
            })
            .setNegativeButton(R.string.disgard_button_text, (dialogInterface, i) -> {
                dismiss();
            });
        return builder.create();
    }

    private void createAccount() {
        try {
            String accountName = accountNameInput.getText().toString();
            double balance = Double.parseDouble(balanceInput.getText().toString());
            Account account = new Account(accountName, balance);
            repository.create(account);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void updateAccount() {
        try {
            String accountName = accountNameInput.getText().toString();
            double balance = Double.parseDouble(balanceInput.getText().toString());
            Account account = new Account(updatingAccount.getId(), accountName, balance);
            repository.update(account);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }
}
