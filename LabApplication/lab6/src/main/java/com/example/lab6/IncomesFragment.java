package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.adapters.IncomesAdapter;
import com.example.lab6.core.models.Income;
import com.example.lab6.core.repositories.IncomesRepository;
import com.example.lab6.dialogs.ProcessIncomeDialog;

import java.util.ArrayList;

public class IncomesFragment extends Fragment {
    Button addButton;
    GridView incomesList;

    private View self;

    private IncomesRepository repository;

    public IncomesFragment() {
        super(R.layout.fragment_incomes);
    }

    public IncomesFragment(IncomesRepository repository) {
        super(R.layout.fragment_incomes);
        this.repository = repository;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.self = view;
        initializeCreationButton(view);
        fillGridView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public IncomesRepository getRepository() {
        return repository;
    }

    public void updateContent() {
        fillGridView(self);
    }

    private void initializeCreationButton(View view) {
        addButton = view.findViewById(R.id.add_income_button);
        addButton.setOnClickListener(buttonView -> {
            ProcessIncomeDialog dialog = new ProcessIncomeDialog(this, repository);
            dialog.show(getParentFragmentManager(), "addIncome");
        });
    }

    private void fillGridView(View view) {
        incomesList = view.findViewById(R.id.incomes_list);
        ArrayList<Income> incomes = repository.getAll();
        IncomesAdapter adapter = new IncomesAdapter(getContext(), incomes, this);
        incomesList.setAdapter(adapter);
    }
}