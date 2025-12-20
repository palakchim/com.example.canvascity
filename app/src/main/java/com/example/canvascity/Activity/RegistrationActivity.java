package com.example.canvascity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvascity.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText etName, etMobile, etEmail, etUsername, etPassword;
    TextInputLayout tilName, tilMobile, tilEmail, tilUsername, tilPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Bind views
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        tilName = findViewById(R.id.tilName);
        tilMobile = findViewById(R.id.tilMobile);
        tilEmail = findViewById(R.id.tilEmail);
        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> validateForm());
    }

    private void validateForm() {

        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Clear old errors
        tilName.setError(null);
        tilMobile.setError(null);
        tilEmail.setError(null);
        tilUsername.setError(null);
        tilPassword.setError(null);

        // Name
        if (name.isEmpty()) {
            tilName.setError("Name is required");
            return;
        }

        // Mobile
        if (mobile.isEmpty() || mobile.length() != 10) {
            tilMobile.setError("Enter valid 10-digit mobile number");
            return;
        }

        // Email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter valid email");
            return;
        }

        // Username
        if (username.isEmpty()) {
            tilUsername.setError("Username is required");
            return;
        }

        // Password
        if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            return;
        }


        registerSuccess();
    }

    private void registerSuccess() {
        Toast.makeText(this, "Registration Successful 🎉", Toast.LENGTH_SHORT).show();

        // Go to Login screen
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
