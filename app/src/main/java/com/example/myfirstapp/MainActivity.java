package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "MyActivity";
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getSharedPreferences("com.example.myfirstapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("FOOBAR", 123);
        editor.apply();
        Log.d(TAG, "Stored FOOBAR to preferences");

        int iFoobar = sharedPref.getInt("FOOBAR", 999);
        Log.d(TAG, "Read FOOBAR value from preferences: " + iFoobar);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL); // (Sensor.TYPE_ALL);

        for(int i=0; i<deviceSensors.size(); i++)
        {
            Log.d(TAG, "Sensor info: " + deviceSensors.get(i).getName());
        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Called when the user taps the Pressure button */
    public void switchToPressureView(View view) {
        Intent intent = new Intent(this, PressureViewActivity.class);
        startActivity(intent);
    }
}