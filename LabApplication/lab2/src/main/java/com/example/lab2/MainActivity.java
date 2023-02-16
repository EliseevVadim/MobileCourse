package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2.model.SpinnerFriendlyKeyValuePair;

public class MainActivity extends AppCompatActivity {

    Spinner colorSelector;
    Spinner modelSelector;
    Spinner equipmentSelector;
    Spinner engineSelector;
    CheckBox autopilotChecker;
    TextView resultField;
    private static final int autopilotPrice = 6000;
    SpinnerFriendlyKeyValuePair[] models = {
        new SpinnerFriendlyKeyValuePair("Model 3", 43990),
        new SpinnerFriendlyKeyValuePair("Model 3 Performance", 64190),
        new SpinnerFriendlyKeyValuePair("Model S", 94990),
        new SpinnerFriendlyKeyValuePair("Model S Plaid", 114990),
        new SpinnerFriendlyKeyValuePair("Model X", 109990),
        new SpinnerFriendlyKeyValuePair("Model X Plaid", 119990),
        new SpinnerFriendlyKeyValuePair("Model Y Long Range", 52190),
        new SpinnerFriendlyKeyValuePair("Model Y Performance", 56190)
    };
    SpinnerFriendlyKeyValuePair[] colors = {
        new SpinnerFriendlyKeyValuePair("Красный", 0),
        new SpinnerFriendlyKeyValuePair("Белый", 300),
        new SpinnerFriendlyKeyValuePair("Черный", 500),
        new SpinnerFriendlyKeyValuePair("Синий", 100),
    };
    SpinnerFriendlyKeyValuePair[] equipmentOptions = {
        new SpinnerFriendlyKeyValuePair("Минимальная", 2000),
        new SpinnerFriendlyKeyValuePair("Обычная", 7000),
        new SpinnerFriendlyKeyValuePair("Полная", 15000)
    };
    SpinnerFriendlyKeyValuePair[] engineTypes = {
        new SpinnerFriendlyKeyValuePair("Двухмоторный полноприводный", 1000),
        new SpinnerFriendlyKeyValuePair("Трехмоторный полноприводный", 7000)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorSelector = findViewById(R.id.colorSpinner);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSelector.setAdapter(colorAdapter);
        modelSelector = findViewById(R.id.modelSpinner);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, models);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSelector.setAdapter(modelAdapter);
        equipmentSelector = findViewById(R.id.equipmentSpinner);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> equipmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipmentOptions);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentSelector.setAdapter(equipmentAdapter);
        engineSelector = findViewById(R.id.engineTypeSpinner);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> engineAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, engineTypes);
        engineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engineSelector.setAdapter(engineAdapter);
        autopilotChecker = findViewById(R.id.autopilotChecker);
        resultField = findViewById(R.id.priceField);
    }

    public void imageButtonClick(View view) {
        Toast
            .makeText(this, "Заполните все поля, чтобы узнать цену автомобиля", Toast.LENGTH_SHORT)
            .show();
    }

    @SuppressLint("DefaultLocale")
    public void calculatePrice(View view) {
        int modelAppend = models[modelSelector.getSelectedItemPosition()].getValue();
        int colorAppend = colors[colorSelector.getSelectedItemPosition()].getValue();
        int equipmentAppend = equipmentOptions[equipmentSelector.getSelectedItemPosition()].getValue();
        int engineAppend = engineTypes[engineSelector.getSelectedItemPosition()].getValue();
        int autopilotAppend = autopilotChecker.isChecked() ? autopilotPrice : 0;
        int fullPrice = modelAppend + colorAppend + equipmentAppend + engineAppend + autopilotAppend;
        resultField.setText(String.format("%d $", fullPrice));
    }
}