package com.example.canvascity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.canvascity.EventFullDetailsActivity;


import androidx.appcompat.app.AppCompatActivity;

public class EventFullDetailsActivity extends AppCompatActivity {

    TextView tvAboutTitle, tvAboutDescription, tvLocationDate, tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_full_details);

        // 🔹 View initialization (IDs SAME as XML)
        tvAboutTitle = findViewById(R.id.tvAboutTitle);
        tvAboutDescription = findViewById(R.id.tvAboutDescription);
        tvLocationDate = findViewById(R.id.tvLocationDate);
        tvPrice = findViewById(R.id.tvPrice);

        // 🔹 Receive data safely
        if (getIntent() != null) {
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String locationDate = getIntent().getStringExtra("locationDate");
            String price = getIntent().getStringExtra("price");

            if (title != null) tvAboutTitle.setText(title);
            if (description != null) tvAboutDescription.setText(description);
            if (locationDate != null) tvLocationDate.setText(locationDate);
            if (price != null) tvPrice.setText(price);
        }
    }
}
