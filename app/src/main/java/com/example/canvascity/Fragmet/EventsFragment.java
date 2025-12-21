package com.example.canvascity.Fragmet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.canvascity.Activity.AboutUsActivity;
import com.example.canvascity.Activity.ProfileActivity;
import com.example.canvascity.Activity.LocationActivity;
import com.example.canvascity.Activity.SettingsActivity;
import com.example.canvascity.R;


public class EventsFragment extends Fragment {

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ VERY IMPORTANT
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    // ✅ Inflate top 3-dot menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_events, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // ✅ Handle menu clicks (IF–ELSE style)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {

            startActivity(new Intent(getActivity(), ProfileActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_about) {

            startActivity(new Intent(getActivity(), AboutUsActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_invite) {

            return true;

        } else if (item.getItemId() == R.id.menu_saved) {

            startActivity(new Intent(getActivity(), LocationActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_settings) {

            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_report) {

            return true;

        } else if (item.getItemId() == R.id.menu_logout) {

            showLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ✅ Logout dialog (Fragment-safe)
    private void showLogoutDialog() {

        new AlertDialog.Builder(getActivity())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
                    // signOutUser(); // if you have it
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
