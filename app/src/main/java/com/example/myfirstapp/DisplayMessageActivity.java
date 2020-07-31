package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private static final String TAG = "DisplayMessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        // Because shared preferences are used, this can read value set in MainActivity
        SharedPreferences sharedPref = this.getSharedPreferences("com.example.myfirstapp", Context.MODE_PRIVATE);
        int iFoobar = sharedPref.getInt("FOOBAR", 999);
        Log.d(TAG, "Read FOOBAR value from preferences in DisplayMessageActivity: " + iFoobar);
    }
}