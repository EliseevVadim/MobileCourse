package com.example.lab4_individual.utils;

import android.graphics.Matrix;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class InformativeTranslateAnimation extends TranslateAnimation {
    private final float[] matrixValues = new float[9];
    private float actualX;
    private float actualY;

    public InformativeTranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue) {
        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        t.getMatrix().getValues(matrixValues);
        actualX = matrixValues[Matrix.MTRANS_X];
        actualY = matrixValues[Matrix.MTRANS_Y];
    }

    public float getActualX() {
        return actualX;
    }

    public float getActualY() {
        return actualY;
    }
}
