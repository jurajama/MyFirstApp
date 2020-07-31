package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final String TAG = "AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // https://www.androidauthority.com/get-use-location-data-android-app-625012/
        // https://www.vogella.com/tutorials/AndroidLocationAPI/article.html

        // Google location API initialization
        // https://developer.android.com/training/location/retrieve-current
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Location object: https://developer.android.com/reference/android/location/Location
                            Log.d(TAG, "Location successfully received: " + location.toString());

                            Toast.makeText(LocationActivity.this,
                                    "Location: " + location.toString(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Log.e(TAG, "Could not get location");

                            Toast.makeText(LocationActivity.this,
                                    "Could not get location",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}