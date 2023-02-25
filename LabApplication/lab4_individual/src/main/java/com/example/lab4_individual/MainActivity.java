package com.example.lab4_individual;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.lab4_individual.utils.InformativeTranslateAnimation;


public class MainActivity extends AppCompatActivity {
    private static final double GRAVITY_ACCELERATION = 9.81;
    private static final int ANIMATION_AREA_HEIGHT = 316;
    private boolean animationPlaying;
    InformativeTranslateAnimation shotFirstPart;
    InformativeTranslateAnimation shotLastPart;
    InformativeTranslateAnimation currentAnimation;
    ImageView missile;
    ImageView wall;
    EditText speedField;
    EditText angleField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        missile = findViewById(R.id.missile);
        wall = findViewById(R.id.wall);
        speedField = findViewById(R.id.speedField);
        angleField = findViewById(R.id.angleField);
        animationPlaying = false;
    }

    public void shoot(View view) {
        try {
            int speed = Integer.parseInt(speedField.getText().toString());
            float angle = (float) Math.toRadians(Integer.parseInt(angleField.getText().toString()));
            float animationTime = (float) (((2 * speed * Math.sin(angle)) / GRAVITY_ACCELERATION) * 1000);
            float xDistance = (float) ((Math.pow(speed, 2) * Math.sin(2 * angle)) / GRAVITY_ACCELERATION);
            float xTop = xDistance / 2;
            float yTop = (float) ((Math.pow(speed, 2) * Math.pow(Math.sin(angle), 2)) / (2 * GRAVITY_ACCELERATION));
            yTop = ANIMATION_AREA_HEIGHT - yTop;
            shotFirstPart = new InformativeTranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.ABSOLUTE, xTop, Animation.RELATIVE_TO_PARENT, 1f, Animation.ABSOLUTE, yTop);
            shotLastPart = new InformativeTranslateAnimation(Animation.ABSOLUTE, xTop, Animation.ABSOLUTE, xDistance, Animation.ABSOLUTE, yTop, Animation.RELATIVE_TO_PARENT, 1f);
            shotFirstPart.setDuration((long) (animationTime / 2));
            shotLastPart.setDuration((long) (animationTime / 2));
            shotFirstPart.setFillAfter(true);shotLastPart.setFillAfter(true);
            currentAnimation = shotFirstPart;
            Runnable collisionChecker = () -> {
                Rect wallCollider = new Rect(wall.getLeft(), wall.getTop(), wall.getRight(), wall.getBottom());
                int missileWidth = missile.getWidth();
                int missileHeight = missile.getHeight();
                Rect missileCollider = new Rect();
                while (animationPlaying) {
                    missileCollider.left = (int) currentAnimation.getActualX();
                    missileCollider.top = (int) currentAnimation.getActualY();
                    missileCollider.right = missileCollider.left + missileWidth;
                    missileCollider.bottom = missileCollider.top + missileHeight;
                    if (Rect.intersects(wallCollider, missileCollider)) {
                        animationPlaying = false;
                        missile.clearAnimation();
                    }
                }
            };
            shotFirstPart.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    missile.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    currentAnimation = shotLastPart;
                    missile.startAnimation(shotLastPart);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            shotLastPart.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    missile.setVisibility(View.GONE);
                    showMessage("Полет завершен");
                    animationPlaying = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            missile.startAnimation(shotFirstPart);
            animationPlaying = true;
            Thread collisionCheckingThread = new Thread(collisionChecker);
            collisionCheckingThread.start();
        }
        catch (Exception exception) {
            showMessage("Произошла ошибка ввода. Симуляция невозможна.");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show();
    }
}