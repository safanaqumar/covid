package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash_screen extends AppCompatActivity {
    private ImageView splashlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        splashlogo.startAnimation(animation);

        final Intent intent = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {

            public void run() {

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();

    }

    private void init() {
        splashlogo = findViewById(R.id.splash_logo);
    }
}
