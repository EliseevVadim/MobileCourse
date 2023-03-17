package com.example.lab6.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lab6.LoansFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Loan;
import com.example.lab6.core.repositories.LoansRepository;

import java.util.Date;

public class ProcessLoanDialog extends DialogFragment {
    EditText loanNameInput;
    EditText loanQuantityInput;
    DatePicker loanDeadLineInput;

    private Loan updatingLoan;
    private final LoansRepository repository;
    private final LoansFragment parent;

    public ProcessLoanDialog(LoansFragment parent, LoansRepository repository) {
        super();
        this.parent = parent;
        this.repository = repository;
    }

    public ProcessLoanDialog(LoansFragment parent, LoansRepository repository, Loan loan) {
        super();
        this.parent = parent;
        this.updatingLoan = loan;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.loan_processor, null));
        loanNameInput = view.findViewById(R.id.loan_name_input);
        loanQuantityInput = view.findViewById(R.id.loan_quantity_input);
        loanDeadLineInput = view.findViewById(R.id.loan_deadLine_input);
        if (updatingLoan != null) {
            loanNameInput.setText(updatingLoan.getName());
            loanQuantityInput.setText(Double.toString(updatingLoan.getQuantity()));
            loanDeadLineInput.init(
                updatingLoan.getDeadLine().getYear(),
                updatingLoan.getDeadLine().getMonth(),
                updatingLoan.getDeadLine().getDate(),
                null
            );
        }
        builder.setView(view)
            .setTitle(updatingLoan == null ? "Создать задолженность" : "Изменить задолженность")
            .setPositiveButton(updatingLoan == null ? R.string.add_button_text : R.string.update_button_text, (dialogInterface, i) -> {
                if (updatingLoan == null)
                    createLoan();
                else
                    updateLoan();
                parent.updateContent();
                dismiss();
            })
            .setNegativeButton(R.string.disgard_button_text, (dialogInterface, i) -> {
                dismiss();
            });
        return builder.create();
    }

    private void createLoan() {
        try {
            String loanName = loanNameInput.getText().toString();
            double quantity = Double.parseDouble(loanQuantityInput.getText().toString());
            Date deadLine = new Date(
                loanDeadLineInput.getYear(),
                loanDeadLineInput.getMonth(),
                loanDeadLineInput.getDayOfMonth()
            );
            Loan loan = new Loan(loanName, quantity, deadLine);
            repository.create(loan);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void updateLoan() {
        try {
            String loanName = loanNameInput.getText().toString();
            double quantity = Double.parseDouble(loanQuantityInput.getText().toString());
            Date deadLine = new Date(
                loanDeadLineInput.getYear(),
                loanDeadLineInput.getMonth(),
                loanDeadLineInput.getDayOfMonth()
            );
            Loan loan = new Loan(updatingLoan.getId(), loanName, quantity, deadLine);
            repository.update(loan);
        }
        catch (Exception ignored) {
            Toast
                .makeText(getContext(), R.string.common_input_error_message, Toast.LENGTH_SHORT)
                .show();
        }
    }
}
