package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView sunView = findViewById(R.id.sun);
        Animation sunriseAnimation = AnimationUtils.loadAnimation(this, R.anim.sunrise);
        sunView.setAnimation(sunriseAnimation);
    }
}