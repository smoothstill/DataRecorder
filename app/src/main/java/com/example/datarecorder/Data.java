package com.example.datarecorder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.location.Location;

/**
 * Instances of data class are used to store timestamp, GPS and accelerometer values.
 * Can print their values in string format
 * Can print their values as .csv row
 * Can print the titles of values as .csv row
 */
public class Data {

    private double latitude;
    private double longitude;
    private float bearing;
    private double altitude; //as meters
    private float speed; //as km/h
    private float locationAccuracy; //as meters

    private float accelerometerX;
    private float accelerometerY;
    private float accelerometerZ;

    private String timeStamp;
    //private long timeStamp;   //Storing timestamps as long would probably save some memory

    /**
     * Construct data object. If GPS or Accelerometer data is not available, stores their values as NaN
     * @param location location (gpsTracker.getLocation())
     * @param accelerometerValues accelerometer x,y,z values (accelerometerTracker.getValues())
     */
    //@SuppressLint("SimpleDateFormat")
    public Data(Location location, ArrayList<Float> accelerometerValues) {

        this.timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (location != null) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
            this.bearing = location.getBearing();
            this.altitude = location.getAltitude();
            this.speed = location.getSpeed();
            this.locationAccuracy = location.getAccuracy();
        }
        else {
            this.latitude = Double.NaN;
            this.longitude = Double.NaN;
            this.bearing = Float.NaN;
            this.altitude = Double.NaN;
            this.speed = Float.NaN;
            this.locationAccuracy = Float.NaN;
        }
        if (accelerometerValues != null && accelerometerValues.size() >= 3) {
            if (accelerometerValues.get(0) != null) this.accelerometerX = accelerometerValues.get(0);
            else this.accelerometerX = Float.NaN;
            if (accelerometerValues.get(1) != null) this.accelerometerY = accelerometerValues.get(1);
            else this.accelerometerY = Float.NaN;
            if (accelerometerValues.get(2) != null) this.accelerometerZ = accelerometerValues.get(2);
            else this.accelerometerY = Float.NaN;
        }
        else {
            this.accelerometerX = Float.NaN;
            this.accelerometerY = Float.NaN;
            this.accelerometerZ = Float.NaN;
        }
    }

    /**
     * Show object's data in a pretty String format
     * @return object's data in String format
     */
    @Override
    public String toString() {

        String data =
                "Timestamp: " + timeStamp + "\n" +
                "GPS:\n" +
                String.format("\tposition:(%1$s, %2$s)\n" +
                        "\tbearing: %3$s\n" +
                        "\taltitude: %4$s\n" +
                        "\tspeed %5$s km/h\n" +
                        "\tlocation accuracy: %6$s m\n", latitude, longitude, bearing, altitude, speed, locationAccuracy) +
                        "Accelerometer:\n" +
                        String.format("\tx value: %1$s\n" +
                                "\ty value: %2$s\n" +
                                "\tz value: %3$s\n", accelerometerX, accelerometerY, accelerometerZ);
        return data;
    }


    /**
     * Return object's data as a .csv file row
     * @return .csv file row as String
     */
    public String toCSVRow() {

        String data = String.format("%1$s;%2$s;%3$s;%4$s;%5$s;%6$s;%7$s;%8$s;%9$s;%10$s\r\n", timeStamp, latitude, longitude, bearing, altitude, speed, locationAccuracy, accelerometerX, accelerometerY, accelerometerZ);

        return data;
    }

    /**
     * Return title row for .csv file
     * @return title row for .csv file
     */
    public static String CSVTitles() {
        return "Timestamp;Latitude;Longitude;Bearing;Altitude;Speed (km/h);Location accuracy (in meters);Value X;Value Y;Value Z\r\n";
    }


}
