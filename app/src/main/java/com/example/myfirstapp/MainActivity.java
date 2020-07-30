package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// This is for nonnull annotation
import androidx.annotation.NonNull;

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

    private static int FINE_LOCATION_REQUEST_CODE = 123;

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

    @Override
    protected void onResume() {
        super.onResume();

        // REST API testing, fetching time every time main view becomes visible
        getApiData();

        /*
         NOTE: Internet access permission defined in this case in AndroidManifest.xml
         is "normal permission" that does not need explicit acceptance by user.
         However for example using camera would need that as that is "dangerous permission"
         https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
        */
    }

    private void getApiData() {
        (Api.getClient().getTodoList()).enqueue(new Callback<List<ApiTestResponse>>() {
            @Override
            public void onResponse(Call<List<ApiTestResponse>> call, Response<List<ApiTestResponse>> response) {
                Log.d("responseGET", String.valueOf(response.body().size()));
                apiResponseData = response.body();

                Toast.makeText(MainActivity.this,
                        "REST API fetch successful, number of items: " + String.valueOf(apiResponseData.size()),
                        Toast.LENGTH_SHORT)
                        .show();

                for(int i=0; i<apiResponseData.size(); i++)
                {
                    ApiTestResponse item = response.body().get(i);
                    Log.d("responseGET", String.valueOf(item.getId()) + ":" + item.getTitle());
                }

                // Process data here, for example set UI elements
            }

            @Override
            public void onFailure(Call<List<ApiTestResponse>> call, Throwable t) {
                // if error occurs in network transaction then we can get the error in this method.

                Log.e("responseGET", "REST API error:\n" + t);

                Toast.makeText(MainActivity.this,
                        "Failed to fetch data from REST API",
                        Toast.LENGTH_SHORT)
                        .show();
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

    /** Called when the user taps the Location button */
    public void switchToLocationView(View view) {
        // Second parameter is just application specific constant to identify this request in callback
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_REQUEST_CODE);

        // Activity is not started here because it would block the visibility of permissions dialogue
        // Instead the activity is started in checkPermission or access granted callback
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG,"requesting permission");
            ActivityCompat
                    .requestPermissions(
                            MainActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(MainActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();

            if(requestCode==FINE_LOCATION_REQUEST_CODE) {
                Intent intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
            }
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == FINE_LOCATION_REQUEST_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(MainActivity.this,
                        "Fine Location Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();

                if(requestCode==FINE_LOCATION_REQUEST_CODE) {
                    Intent intent = new Intent(this, LocationActivity.class);
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Fine Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}