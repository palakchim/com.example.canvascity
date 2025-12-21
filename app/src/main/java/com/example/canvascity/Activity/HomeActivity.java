package com.example.canvascity.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.canvascity.Fragmet.EventsFragment;
import com.example.canvascity.Fragmet.SmallBisFragment;
import com.example.canvascity.Fragmet.WardrobeFragment;
import com.example.canvascity.Fragmet.YourAiFragment;
import com.example.canvascity.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_events);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    EventsFragment ef=new EventsFragment();
    WardrobeFragment wf=new WardrobeFragment();

    SmallBisFragment sbf=new SmallBisFragment();
    YourAiFragment yaf=new YourAiFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_events)
        {getSupportFragmentManager().beginTransaction().replace(R.id.container,ef).commit();}
        else if(item.getItemId()==R.id.nav_wardrobe)        {getSupportFragmentManager().beginTransaction().replace(R.id.container,wf).commit();}

        else if(item.getItemId()==R.id.nav_small_business)         {getSupportFragmentManager().beginTransaction().replace(R.id.container,sbf).commit();}

        else if(item.getItemId()==R.id.nav_ai)        {getSupportFragmentManager().beginTransaction().replace(R.id.container,yaf).commit();}


        return true;
    }
}