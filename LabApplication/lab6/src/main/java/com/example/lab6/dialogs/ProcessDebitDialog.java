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

import com.example.lab6.DebitsFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Debit;
import com.example.lab6.core.repositories.DebitsRepository;

public class ProcessDebitDialog extends DialogFragment {
    EditText debitNameInput;
    EditText debitQuantityInput;

    private Debit updatingDebit;
    private final DebitsRepository repository;
    private final DebitsFragment parent;

    public ProcessDebitDialog(DebitsFragment parent, DebitsRepository repository) {
        super();
        this.parent = parent;
        this.repository = repository;
    }

    public ProcessDebitDialog(DebitsFragment parent, DebitsRepository repository, Debit debit) {
        super();
        this.parent = parent;
        this.updatingDebit = debit;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.debit_processor, null));
        debitNameInput = view.findViewById(R.id.debit_name_input);
        debitQuantityInput = view.findViewById(R.id.debit_quantity_input);
        if (updatingDebit != null) {
            debitNameInput.setText(updatingDebit.getName());
            debitQuantityInput.setText(Double.toString(updatingDebit.getQuantity()));
        }
        builder.setView(view)
            .setTitle(updatingDebit == null ? "Создать дебет" : "Изменить дебет")
            .setPositiveButton(updatingDebit == null ? R.string.add_button_text : R.string.update_button_text, (dialogInterface, i) -> {
                if (updatingDebit == null)
                    createDebit();
                else
                    updateDebit();
                parent.updateContent();
                dismiss();
            })
            .setNegativeButton(R.string.disgard_button_text, (dialogInterface, i) -> {
                dismiss();
            });
        return builder.create();
    }

    private void createDebit() {
        try {
            String debitName = debitNameInput.getText().toString();
            double quantity = Double.parseDouble(debitQuantityInput.getText().toString());
            Debit debit = new Debit(debitName, quantity);
            repository.create(debit);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void updateDebit() {
        try {
            String debitName = debitNameInput.getText().toString();
            double quantity = Double.parseDouble(debitQuantityInput.getText().toString());
            Debit debit = new Debit(updatingDebit.getId(), debitName, quantity);
            repository.update(debit);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }
}
