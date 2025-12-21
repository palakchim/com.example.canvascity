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
import com.example.canvascity.Activity.SettingsActivity;
import com.example.canvascity.R;

public class WardrobeFragment extends Fragment {

    public WardrobeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ✅ Enable fragment menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);
    }

    // ✅ Inflate top 3-dot menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_wardrobe, menu); // your menu XML file
        super.onCreateOptionsMenu(menu, inflater);
    }

    // ✅ Handle menu clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_cart) {
            Toast.makeText(getActivity(), "Cart clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_wardrobe) {
            Toast.makeText(getActivity(), "Your Wardrobe clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_wishlist) {
            Toast.makeText(getActivity(), "Wishlist clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_seller) {
            Toast.makeText(getActivity(), "Become a Seller clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_about) {
            startActivity(new Intent(getActivity(), AboutUsActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_report) {
            Toast.makeText(getActivity(), "Report clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_logout) {
            showLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ✅ Logout dialog
    private void showLogoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
                    // signOutUser(); // optional
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
