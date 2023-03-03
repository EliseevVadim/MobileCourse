package com.example.lab5.model;

public class UserInput {
    private final int modelIndex;
    private final int colorIndex;
    private final int equipmentOptionIndex;
    private final int engineTypeIndex;
    private final boolean autopilotRequired;

    public UserInput(int modelIndex, int colorIndex, int equipmentOptionIndex, int engineTypeIndex, boolean autopilotRequired) {
        this.modelIndex = modelIndex;
        this.colorIndex = colorIndex;
        this.equipmentOptionIndex = equipmentOptionIndex;
        this.engineTypeIndex = engineTypeIndex;
        this.autopilotRequired = autopilotRequired;
    }

    public int getModelIndex() {
        return modelIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public int getEquipmentOptionIndex() {
        return equipmentOptionIndex;
    }

    public int getEngineTypeIndex() {
        return engineTypeIndex;
    }

    public boolean isAutopilotRequired() {
        return autopilotRequired;
    }
}
