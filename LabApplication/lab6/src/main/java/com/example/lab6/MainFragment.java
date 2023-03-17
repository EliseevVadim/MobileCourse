package com.example.lab6;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab6.core.RecalculationManager;

public class MainFragment extends Fragment {
    Button recalculationButton;
    private RecalculationManager recalculationManager;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    public MainFragment(RecalculationManager recalculationManager) {
        super(R.layout.fragment_main);
        this.recalculationManager = recalculationManager;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initializeRecalculationButton(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeRecalculationButton(View view) {
        recalculationButton = view.findViewById(R.id.recalculation_button);
        recalculationButton.setOnClickListener(buttonView -> {
            new AlertDialog.Builder(getContext())
                .setTitle("Начать перерасчет?")
                .setMessage("При перерасчете количество денег на балансе будет обновлено в " +
                    "соответствии с указанными данными о доходах и расходах, а также соответствующие " +
                    "данные будут удалены. Продолжить?")
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    try {
                        double[] state = recalculationManager.recalculateState();
                        new AlertDialog.Builder(getContext())
                            .setTitle("Данные о балансе")
                            .setMessage(String.format("Ваш баланс:\n" +
                                "Сумма по счетам: %s\n" +
                                "Сумма по накоплениям: %s",
                                String.format("%1$,.2f", state[0]),
                                String.format("%1$,.2f", state[1])))
                            .show();
                    }
                    catch (IllegalStateException ignored) {
                        Toast
                            .makeText(getContext(),"Введенные Вами сведения о счетах, доходах и расходах " +
                            "приводят к отрицательному балансу. Действие невозможно, " +
                            "перепроверьте ввод.", Toast.LENGTH_LONG)
                            .show();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
        });
    }
}