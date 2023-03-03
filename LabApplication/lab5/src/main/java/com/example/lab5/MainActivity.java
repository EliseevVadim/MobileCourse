package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab5.model.SpinnerFriendlyKeyValuePair;
import com.example.lab5.model.UserInput;
import com.example.lab5.service.Reader;
import com.example.lab5.service.Writer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Spinner colorSelector;
    Spinner modelSelector;
    Spinner equipmentSelector;
    Spinner engineSelector;
    CheckBox autopilotChecker;
    TextView resultField;
    private static final int autopilotPrice = 6000;
    SpinnerFriendlyKeyValuePair[] models;
    SpinnerFriendlyKeyValuePair[] colors;
    SpinnerFriendlyKeyValuePair[] equipmentOptions;
    SpinnerFriendlyKeyValuePair[] engineTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initializeSpinnerOptions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        linkResourcesToSpinners();
        autopilotChecker = findViewById(R.id.autopilotChecker);
        resultField = findViewById(R.id.priceField);
        try {
            loadLastUserInput();
        } catch (IOException ignored) {}
    }

    @Override
    protected void onPause() {
        try {
            saveUserInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onPause();
    }

    private void initializeSpinnerOptions() throws IOException {
        Reader reader = new Reader(this);
        models = reader.readSpinnerContentFromJson(getString(R.string.models_content_path));
        colors = reader.readSpinnerContentFromJson(getString(R.string.colors_content_path));
        equipmentOptions = reader.readSpinnerContentFromJson(getString(R.string.equipment_options_content_path));
        engineTypes = reader.readSpinnerContentFromJson(getString(R.string.engine_types_content_path));
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

    private void linkResourcesToSpinners() {
        colorSelector = findViewById(R.id.colorSpinner);
        modelSelector = findViewById(R.id.modelSpinner);
        equipmentSelector = findViewById(R.id.equipmentSpinner);
        engineSelector = findViewById(R.id.engineTypeSpinner);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> colorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        colorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSelector.setAdapter(colorsAdapter);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> modelsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, models);
        modelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSelector.setAdapter(modelsAdapter);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> equipmentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipmentOptions);
        equipmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentSelector.setAdapter(equipmentsAdapter);
        ArrayAdapter<SpinnerFriendlyKeyValuePair> enginesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, engineTypes);
        enginesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engineSelector.setAdapter(enginesAdapter);
    }

    private void saveUserInput() throws IOException {
        UserInput userInput = new UserInput(
            modelSelector.getSelectedItemPosition(),
            colorSelector.getSelectedItemPosition(),
            equipmentSelector.getSelectedItemPosition(),
            engineSelector.getSelectedItemPosition(),
            autopilotChecker.isChecked()
        );
        Writer writer = new Writer(this);
        writer.saveUserInputToFile(userInput, getString(R.string.user_input_content_path));
    }

    private void loadLastUserInput() throws IOException {
        Reader reader = new Reader(this);
        UserInput lastInput = reader.loadUserInput(getString(R.string.user_input_content_path));
        modelSelector.setSelection(lastInput.getModelIndex());
        colorSelector.setSelection(lastInput.getColorIndex());
        equipmentSelector.setSelection(lastInput.getEquipmentOptionIndex());
        engineSelector.setSelection(lastInput.getEngineTypeIndex());
        autopilotChecker.setChecked(lastInput.isAutopilotRequired());
    }
}