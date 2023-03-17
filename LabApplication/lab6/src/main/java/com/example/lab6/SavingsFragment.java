package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.adapters.SavingsAdapter;
import com.example.lab6.core.models.Saving;
import com.example.lab6.core.repositories.SavingsRepository;
import com.example.lab6.dialogs.ProcessSavingDialog;

import java.util.ArrayList;

public class SavingsFragment extends Fragment {
    Button addButton;
    GridView savingsList;

    private View self;

    private SavingsRepository repository;

    public SavingsFragment() {
        super(R.layout.fragment_savings);
    }

    public SavingsFragment(SavingsRepository repository) {
        super(R.layout.fragment_savings);
        this.repository = repository;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.self = view;
        initializeCreationButton(view);
        fillGridView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public SavingsRepository getRepository() {
        return repository;
    }

    public void updateContent() {
        fillGridView(self);
    }

    private void initializeCreationButton(View view) {
        addButton = view.findViewById(R.id.add_account_button);
        addButton.setOnClickListener(buttonView -> {
            ProcessSavingDialog dialog = new ProcessSavingDialog(this, repository);
            dialog.show(getParentFragmentManager(), "addSaving");
        });
    }

    private void fillGridView(View view) {
        savingsList = view.findViewById(R.id.savings_list);
        ArrayList<Saving> savings = repository.getAll();
        SavingsAdapter adapter = new SavingsAdapter(getContext(), savings, this);
        savingsList.setAdapter(adapter);
    }
}