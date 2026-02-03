package com.example.canvascity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.canvascity.EventFullDetailsActivity;


import com.example.canvascity.R;

public class EventDetailsActivity extends AppCompatActivity {

    ImageView imgEvent;
    TextView tvTitle, tvLocationDate, tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // 🔹 Insets (UNCHANGED)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔹 View init (SAFE)
        imgEvent = findViewById(R.id.imgEvent);
        tvTitle = findViewById(R.id.tvAboutTitle);
        tvLocationDate = findViewById(R.id.tvLocation);
        tvPrice = findViewById(R.id.tvPrice);
        CardView cardAbout = findViewById(R.id.cardAbout);

        // 🔹 Get data from intent (SAFE)
        Intent i = getIntent();
        if (i != null) {
            tvTitle.setText(i.getStringExtra("title"));
            tvLocationDate.setText(i.getStringExtra("locationDate"));
            tvPrice.setText(i.getStringExtra("price"));

            int imageRes = i.getIntExtra("image", 0);
            if (imageRes != 0) {
                imgEvent.setImageResource(imageRes);
            }
        }

        // 🔹 About Event click → Full details page
        cardAbout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    EventDetailsActivity.this,
                    EventFullDetailsActivity.class
            );

            // pass data forward
            intent.putExtra("title", i.getStringExtra("title"));
            intent.putExtra("description", i.getStringExtra("description"));
            intent.putExtra("locationDate", i.getStringExtra("locationDate"));
            intent.putExtra("price", i.getStringExtra("price"));

            startActivity(intent);
        });
    }
}
