package com.example.canvascity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvascity.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = findViewById(R.id.ivLogo);
        TextView slogan = findViewById(R.id.tvSlogan);

        // Delay for 3 seconds, then go to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // close splash so user can’t go back
        }, SPLASH_DURATION);
    }
}
