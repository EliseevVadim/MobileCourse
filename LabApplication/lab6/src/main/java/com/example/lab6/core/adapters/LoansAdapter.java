package com.example.lab6.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab6.LoansFragment;
import com.example.lab6.R;
import com.example.lab6.core.models.Loan;

import com.example.lab6.dialogs.ProcessLoanDialog;

import java.util.ArrayList;

public class LoansAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Loan> loans;
    private final LoansFragment parent;

    public LoansAdapter(Context context, ArrayList<Loan> loans, LoansFragment parent) {
        this.context = context;
        this.loans = loans;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return loans.size();
    }

    @Override
    public Object getItem(int i) {
        return loans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.loan_presenter, null);
        TextView idField = view.findViewById(R.id.id_field);
        TextView loanNameField = view.findViewById(R.id.loan_name_field);
        TextView quantityField = view.findViewById(R.id.loan_quantity_field);
        TextView deadLineField = view.findViewById(R.id.loan_deadLine_field);
        Button updateButton = view.findViewById(R.id.updateLoanButton);
        Button deleteButton = view.findViewById(R.id.deleteLoanButton);
        Loan loan = loans.get(i);
        idField.setText(Integer.toString(loan.getId()));
        loanNameField.setText(loan.getName());
        quantityField.setText(String.format("%1$,.2f", loan.getQuantity()));
        deadLineField.setText(String.format("%02d.%02d.%d",
            loan.getDeadLine().getDate(),
            loan.getDeadLine().getMonth() + 1,
            loan.getDeadLine().getYear()));
        updateButton.setOnClickListener(updateButtonView -> {
            ProcessLoanDialog dialog = new ProcessLoanDialog(parent, parent.getRepository(), loan);
            dialog.show(parent.getParentFragmentManager(), "updateLoan");
        });
        deleteButton.setOnClickListener(deleteButtonView -> {
            parent.getRepository().delete(loan.getId());
            parent.updateContent();
        });
        return view;
    }
}
