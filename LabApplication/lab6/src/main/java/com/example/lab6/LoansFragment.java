package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.adapters.LoansAdapter;
import com.example.lab6.core.models.Loan;
import com.example.lab6.core.repositories.LoansRepository;
import com.example.lab6.dialogs.ProcessLoanDialog;

import java.util.ArrayList;

public class LoansFragment extends Fragment {
    Button addButton;
    GridView loansList;

    private View self;

    private LoansRepository repository;

    public LoansFragment() {
        super(R.layout.fragment_loans);
    }

    public LoansFragment(LoansRepository repository) {
        super(R.layout.fragment_loans);
        this.repository = repository;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.self = view;
        initializeCreationButton(view);
        fillGridView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public LoansRepository getRepository() {
        return repository;
    }

    public void updateContent() {
        fillGridView(self);
    }

    private void initializeCreationButton(View view) {
        addButton = view.findViewById(R.id.add_loan_button);
        addButton.setOnClickListener(buttonView -> {
            ProcessLoanDialog dialog = new ProcessLoanDialog(this, repository);
            dialog.show(getParentFragmentManager(), "addLoan");
        });
    }

    private void fillGridView(View view) {
        loansList = view.findViewById(R.id.loans_list);
        ArrayList<Loan> loans = repository.getAll();
        LoansAdapter adapter = new LoansAdapter(getContext(), loans, this);
        loansList.setAdapter(adapter);
    }
}