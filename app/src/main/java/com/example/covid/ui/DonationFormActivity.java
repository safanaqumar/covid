package com.example.covid.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.covid.R;
import com.example.covid.map.SelectLocationOnMapActivity;

public class DonationFormActivity extends AppCompatActivity {
    private static final int WORK_PLACE_LOCATION_SELECT = 123;
    private double latitude;
    private double longitude;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
    }

    public void onSubmitClicked(View view) {
    }

    public void onLocationButtonClicked(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DonationFormActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        123);
                return;
            }
        }
        Intent i = new Intent(DonationFormActivity.this, SelectLocationOnMapActivity.class);
        startActivityForResult(i, WORK_PLACE_LOCATION_SELECT);
    }

    public void onDonationRequestClicked(View view) {
        Intent i = new Intent(this, MyDonationRequestActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WORK_PLACE_LOCATION_SELECT) {
            if (resultCode == RESULT_OK) {
                latitude = data.getDoubleExtra("lat", 0.0);
                longitude = data.getDoubleExtra("lng", 0.0);
                city = data.getStringExtra("city");
            }
        }
    }
}