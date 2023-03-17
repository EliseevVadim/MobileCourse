package com.example.lab6.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lab6.R;
import com.example.lab6.SavingsFragment;
import com.example.lab6.core.models.Account;
import com.example.lab6.core.models.Saving;
import com.example.lab6.core.repositories.SavingsRepository;

import java.util.ArrayList;

public class ProcessSavingDialog extends DialogFragment {
    EditText savingNameInput;
    EditText quantityInput;
    Spinner accountIdInput;

    private Saving updatingSaving;
    private final SavingsRepository repository;
    private final SavingsFragment parent;

    public ProcessSavingDialog(SavingsFragment parent, SavingsRepository repository) {
        super();
        this.parent = parent;
        this.repository = repository;
    }

    public ProcessSavingDialog(SavingsFragment parent, SavingsRepository repository, Saving saving) {
        super();
        this.parent = parent;
        this.updatingSaving = saving;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.saving_processor, null));
        savingNameInput = view.findViewById(R.id.saving_name_input);
        quantityInput = view.findViewById(R.id.saving_quantity_input);
        accountIdInput = view.findViewById(R.id.saving_accountId_selector);
        ArrayList<Account> accounts = repository.getAccounts();
        ArrayAdapter<Account> accountsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, accounts);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountIdInput.setAdapter(accountsAdapter);
        if (updatingSaving != null) {
            savingNameInput.setText(updatingSaving.getName());
            quantityInput.setText(Double.toString(updatingSaving.getQuantity()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                accountIdInput.setSelection(accountsAdapter.getPosition(accounts
                    .stream()
                    .filter(account -> account.getId() == updatingSaving.getAccountId())
                    .findAny()
                    .get()));
            }
        }
        builder.setView(view)
            .setTitle(updatingSaving == null ? "Создать накопление" : "Изменить накопление")
            .setPositiveButton(updatingSaving == null ? R.string.add_button_text : R.string.update_button_text, (dialogInterface, i) -> {
                if (updatingSaving == null)
                    createSaving();
                else
                    updateSaving();
                parent.updateContent();
                dismiss();
            })
            .setNegativeButton(R.string.disgard_button_text, (dialogInterface, i) -> {
                dismiss();
            });
        return builder.create();
    }

    private void createSaving() {
        try {
            String savingName = savingNameInput.getText().toString();
            double quantity = Double.parseDouble(quantityInput.getText().toString());
            int accountId = ((Account)accountIdInput.getSelectedItem()).getId();
            Saving saving = new Saving(savingName, quantity, accountId);
            repository.create(saving);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void updateSaving() {
        try {
            String savingName = savingNameInput.getText().toString();
            double quantity = Double.parseDouble(quantityInput.getText().toString());
            int accountId = ((Account)accountIdInput.getSelectedItem()).getId();
            Saving saving = new Saving(updatingSaving.getId(), savingName, quantity, accountId);
            repository.update(saving);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }
}
