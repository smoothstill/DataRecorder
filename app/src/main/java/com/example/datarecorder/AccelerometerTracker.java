package com.example.datarecorder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Instances of AccelerometerTracker are used to listen accelerometer data.
 * @author Mika Lammi
 * @version 1.0
 */
public class AccelerometerTracker implements SensorEventListener {

    private Context context;
    private ArrayList<Float> values;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private final String TAG = "MainActivity";

    /**
     * Construct an accelerometer tracker
     * @param context application context
     */
    public AccelerometerTracker(Context context) {
        this.context = context;

        values = new ArrayList<>(Arrays.asList(0.0f, 0.0f, 0.0f));

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    /**
     * Change XYZ values whenever sensor changes
     * @param event sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d(TAG, "x: " + event.values[0] + " y: " + event.values[1] + " z: " + event.values[2]);
        values.set(0, event.values[0]);
        values.set(1, event.values[1]);
        values.set(2, event.values[2]);
    }

    /**
     * Get XYZ values of accelerometer
     * @return accelerometer values
     */
    public final ArrayList<Float> getValues() {
        return values;
    }

    /**
     * stop listening to sensor events
     */
    public void stopListening() {
        sensorManager.unregisterListener(this);
    }

    /**
     * start listening to sensor events
     */
    public void startListening() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
