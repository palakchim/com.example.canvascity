package com.example.canvascity.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canvascity.Activity.*;
import com.example.canvascity.R;
import com.example.canvascity.adapter.EventAdapter;
import com.example.canvascity.model.EventModel;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    RecyclerView recyclerView;
    EventAdapter adapter;
    ArrayList<EventModel> eventList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = view.findViewById(R.id.rvEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventList = new ArrayList<>();

        // ✅ EACH EVENT HAS ITS OWN IMAGE
        eventList.add(new EventModel(
                "Fashion Expo",
                "Mumbai · 12 Feb",
                "₹499",
                "Full fashion event description",
                R.drawable.fashionimage
        ));

        eventList.add(new EventModel(
                "Music Night",
                "Delhi · 13 May 2026",
                "₹799",
                "Music concert details",
                R.drawable.music_event
        ));

        eventList.add(new EventModel(
                "Art & Canvas Workshop",
                "Delhi · 12 Feb",
                "₹399",
                "Creative painting workshop",
                R.drawable.art_event
        ));

        eventList.add(new EventModel(
                "Food Carnival",
                "Mumbai · 18 Nov",
                "₹399",
                "Street food, desserts and live counters",
                R.drawable.food_event
        ));

        eventList.add(new EventModel(
                "Stand-Up Comedy",
                "Bangalore · 12 Jan",
                "₹499",
                "Laugh out loud with top comedians",
                R.drawable.comedy_event
        ));

        eventList.add(new EventModel(
                "Art & Craft Expo",
                "Delhi · 16 June",
                "Free",
                "Handmade art, paintings and workshops",
                R.drawable.craft_event
        ));

        eventList.add(new EventModel(
                "Tech Startup Meetup",
                "Hyderabad · 18 Jan",
                "₹299",
                "Networking with founders and investors",
                R.drawable.tech_event
        ));

        eventList.add(new EventModel(
                "Pottery Workshop",
                "Amravati · 20 Jan",
                "₹399",
                "a perfect day  to spend with your loved ones",
                R.drawable.pottery_event
        ));

        eventList.add(new EventModel(
                "Yoga & Wellness Camp",
                "Rishikesh · 25 Jan",
                "₹599",
                "Relaxing yoga sessions and meditation",
                R.drawable.yoga_event
        ));

        adapter = new EventAdapter(getActivity(), eventList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // MENU CODE UNCHANGED ↓↓↓
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_events, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_about) {
            startActivity(new Intent(getActivity(), AboutUsActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_saved) {
            startActivity(new Intent(getActivity(), LocationActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_logout) {
            showLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (d, w) ->
                        Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show()
                )
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .show();
    }
}
