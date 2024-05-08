package hu.inf.szte.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hu.inf.szte.R;
import hu.inf.szte.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(animation);

        new Thread(() -> {
            try {
                Thread.sleep(3000); // Wait for 3 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }).start();
    }
}