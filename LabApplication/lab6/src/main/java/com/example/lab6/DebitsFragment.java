package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.adapters.DebitsAdapter;
import com.example.lab6.core.models.Debit;
import com.example.lab6.core.repositories.DebitsRepository;
import com.example.lab6.dialogs.ProcessDebitDialog;

import java.util.ArrayList;

public class DebitsFragment extends Fragment {
    Button addButton;
    GridView debitsList;

    private View self;

    private DebitsRepository repository;

    public DebitsFragment() {
        super(R.layout.fragment_debits);
    }

    public DebitsFragment(DebitsRepository repository) {
        super(R.layout.fragment_debits);
        this.repository = repository;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.self = view;
        initializeCreationButton(view);
        fillGridView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public DebitsRepository getRepository() {
        return repository;
    }

    public void updateContent() {
        fillGridView(self);
    }

    private void initializeCreationButton(View view) {
        addButton = view.findViewById(R.id.add_debit_button);
        addButton.setOnClickListener(buttonView -> {
            ProcessDebitDialog dialog = new ProcessDebitDialog(this, repository);
            dialog.show(getParentFragmentManager(), "addDebit");
        });
    }

    private void fillGridView(View view) {
        debitsList = view.findViewById(R.id.debits_list);
        ArrayList<Debit> debits = repository.getAll();
        DebitsAdapter adapter = new DebitsAdapter(getContext(), debits, this);
        debitsList.setAdapter(adapter);
    }
}