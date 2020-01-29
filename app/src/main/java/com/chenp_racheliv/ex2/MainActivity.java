package com.chenp_racheliv.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    GameView gameView;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.viewID);

        mediaPlayer = new MediaPlayer().create(this, R.raw.brick_sound);

        // set FULL SCREEN - need to hide Status & Action Bar
        // ===================================================
        // Hide the Activity Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Activity Action Bar
        getSupportActionBar().hide();

        // set SCREEN ORIENTATION
        // ===================================================
        // set Activity(screen) Orientation to LANDSCAPE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        // 1. get SensorManager from the System SENSOR_SERVICE
        // ---------------------------------------------------
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 2. get the needed Sensors. (if null returns - sensor not exist on device!)
        // --------------------------------------------------------------------------
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 3. check if one of the sensors not exist in the device?
        // --------------------------------------------------------
        if (accelerometerSensor == null)
        {
            String sensorErrMsg = "";
            if (accelerometerSensor == null)
                sensorErrMsg += "\nOrientaion Sensor NOT exists!";

            sensorErrMsg += "\nThe app will exit!";

            Toast.makeText(this, sensorErrMsg, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // start listen to all sensors
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("debug", "onResume");

        gameView.threadIsStopped = false;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // disable all sensors
        sensorManager.unregisterListener(this);
        Log.d("debug", "onPause");

        gameView.threadIsStopped = true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                float x = sensorEvent.values[0]; // Acceleration force along the x axis
                float y = sensorEvent.values[1]; // Acceleration force along the y axis
                float z = sensorEvent.values[2]; // Acceleration force along the z axis
                //Log.d("debug", "Orientation Sensor:\nx-axis(Pitch - left/right) = "+x+"\ny-axis(Roll - up/down) = "+y+"\nz-axis(Azimuth - round) = "+z );
                gameView.update(y);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
