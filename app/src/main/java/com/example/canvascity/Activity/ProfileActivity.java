package com.example.canvascity.Activity;

import static android.icu.text.DisplayOptions.DisplayLength.LENGTH_SHORT;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.canvascity.Common.Urls;
import com.example.canvascity.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       preferences= PreferenceManager.getDefaultSharedPreferences(this);
       editor=preferences.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMyDetails();
    }

    private void getMyDetails() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();//pass or put data
        params.put("email",preferences.getString("email",""));
        client.post(Urls.getMyDetailsWebService,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray=  response.getJSONArray("getMyDetails");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                       JSONObject jsonObject= jsonArray.getJSONObject(i);
                       String strid=jsonObject.getString("id");
                        String strname=jsonObject.getString("name");
                        String stremail=jsonObject.getString("email");
                        String strmobile=jsonObject.getString("mobile");
                        String strpassword=jsonObject.getString("password");
                        String strimage=jsonObject.getString("image");
Toast.makeText(ProfileActivity.this,"image name:"+strimage, Toast.LENGTH_SHORT).show();
Glide.with(ProfileActivity.this)
                                .load("http://ipv4:80/CanvasCityApi/images"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.user)
                                .into(/*imageview ka widget object*/);
//set text krna pdega on all widgets//after creating

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}