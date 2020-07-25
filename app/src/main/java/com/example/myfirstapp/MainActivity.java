package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "MyActivity";
    private SensorManager sensorManager;
    private Sensor pressureSensor = null;
    private boolean bPressureSensorFound = false;

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
            if(deviceSensors.get(i).getType() == Sensor.TYPE_PRESSURE)
                pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        }
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        if(pressureSensor!=null)
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        if(pressureSensor!=null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This function needs to be overriden even if we are not interested on accuracy change events
        // because the activity class shall implement all methods of SensorEventListener interface
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float millibarsOfPressure = event.values[0];
        Log.d(TAG, "onSensorChanged, pressure value: " + millibarsOfPressure);

        TextView textView = findViewById(R.id.pressureDisplay);
        String sPressure = Float.valueOf(millibarsOfPressure).toString() + " mbar";
        textView.setText(sPressure);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}