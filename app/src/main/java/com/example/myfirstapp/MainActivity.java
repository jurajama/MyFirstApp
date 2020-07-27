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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Retrofit example is from Retrofit 2.X GET chapter in https://abhiandroid.com/programming/retrofit

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "MyActivity";
    private SensorManager sensorManager;

    List<ApiTestResponse> apiResponseData;

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

        // REST API testing
        getApiData();

        // NOTE: Internet access permission is "normal permission" that does not need explicit acceptance by user
        // However for example using camera would need that as that is "dangerous permission"
        // https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    }

    private void getApiData() {
        (Api.getClient().getTodoList()).enqueue(new Callback<List<ApiTestResponse>>() {
            @Override
            public void onResponse(Call<List<ApiTestResponse>> call, Response<List<ApiTestResponse>> response) {
                Log.d("responseGET", String.valueOf(response.body().size()));
                apiResponseData = response.body();

                for(int i=0; i<response.body().size(); i++)
                {
                    ApiTestResponse item = response.body().get(i);
                    Log.d("responseGET", String.valueOf(item.getId()) + ":" + item.getTitle());
                }
                // Process data here, for example set UI elements
            }

            @Override
            public void onFailure(Call<List<ApiTestResponse>> call, Throwable t) {
                // if error occurs in network transaction then we can get the error in this method.
                Log.e("responseGET", "ERROR in REST API call");
                Log.e("responseGET", "I got an error", t);
                // TODO: Toast for error message
            }
        });
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