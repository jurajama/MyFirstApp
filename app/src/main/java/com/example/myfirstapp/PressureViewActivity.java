package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PressureViewActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "PressureViewActivity";
    private SensorManager sensorManager;
    private Sensor pressureSensor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
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

        TextView textView = findViewById(R.id.pressureValue);
        String sPressure = Float.valueOf(millibarsOfPressure).toString() + " mbar";
        textView.setText(sPressure);
    }
}