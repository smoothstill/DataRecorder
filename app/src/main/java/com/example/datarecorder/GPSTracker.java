package com.example.datarecorder;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

/**
 *
 * Instances of GPSTracker are used to listen GPS data.
 * @author Mika Lammi
 * @version 1.0
 */
public class GPSTracker implements LocationListener {

    private Context context;
    private Location location;
    private LocationManager lm;

    /**
     * Constructs GPSTracker object
     * @param context application context
     */
    public GPSTracker(Context context) {
        this.context = context;
    }


    /**
     * Returns a Location object
     * @return Location
     */
    public Location getLocation() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }

        lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000, 10, this);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        }
        else {
            Toast.makeText(context, "You must enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
