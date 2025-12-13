package com.example.canvascity.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvascity.Common.Urls;
import com.example.canvascity.DatabaseHelper;
import com.example.canvascity.R;
import com.example.canvascity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMobile, etPassword;

    private Button btnRegister;

    private FirebaseAuth auth;
    private DatabaseHelper dbHelper;

    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper();

        // Initialize views
        etName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               String name = etName.getText().toString().trim();
                                               String email = etEmail.getText().toString().trim();
                                               String mobile = etMobile.getText().toString().trim();
                                               String password = etPassword.getText().toString().trim();
                                               if (TextUtils.isEmpty(name)) {
                                                   etName.setError("Enter name");
                                                   return;
                                               }
                                              else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                                   etEmail.setError("Enter valid email");
                                                   return;
                                               }
                                               else if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
                                                   etMobile.setError("Enter valid mobile number");
                                                   return;
                                               }
                                               else if (TextUtils.isEmpty(password) || password.length() < 6) {
                                                   etPassword.setError("Password must be at least 6 characters");
                                                   return;
                                               }
                                               else
                                               {

                                                   AsyncHttpClient client = new AsyncHttpClient();
                                                   RequestParams params= new RequestParams();
                                                   params.put("name",name);
                                                   params.put("email",email);
                                                   params.put("mobile",mobile);
                                                   params.put("password",password);
                                                   client.post(Urls.registerUsersWebService,params,new JsonHttpResponseHandler()
                                                   {
                                                       @Override
                                                       public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                           super.onSuccess(statusCode, headers, response);
                                                           //String success=
                                                       }

                                                       @Override
                                                       public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                           super.onFailure(statusCode, headers, throwable, errorResponse);
                                                       }
                                                   });
                                               }
                                           }
                                       });

    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Basic validation
        if (TextUtils.isEmpty(name)) {
            etName.setError("Enter name");
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            return;
        }
        if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
            etMobile.setError("Enter valid mobile number");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        // Create FirebaseAuth user
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser firebaseUser = auth.getCurrentUser();
//                        if (firebaseUser != null) {
//                            String uid = firebaseUser.getUid();
//                            User user = new User(uid, name, email, mobile);
//
//                            Log.d(TAG, "Saving user: " + user.getEmail() + " with UID: " + uid);
//
//                            dbHelper.saveUser(user, new DatabaseHelper.DatabaseCallback() {
//                                @Override
//                                public void onSuccess(String message) {
//                                    Log.d(TAG, "Database Save Success: " + message);
//                                    Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
//
//                                    // ✅ Only navigate after DB success
//                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//
//                                @Override
//                                public void onFailure(String error) {
//                                    Log.e(TAG, "Database Save Failed: " + error);
//                                    Toast.makeText(RegistrationActivity.this, "DB Error: " + error, Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                    }
                  else {

                    }
                };
    }

