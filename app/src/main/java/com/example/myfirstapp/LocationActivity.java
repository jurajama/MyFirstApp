package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // https://www.androidauthority.com/get-use-location-data-android-app-625012/
        // https://www.vogella.com/tutorials/AndroidLocationAPI/article.html
    }
}