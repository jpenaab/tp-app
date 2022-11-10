package org.hopto.tiempoplaya.location_services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.*;


/**
 * Created by jpenaab on 04/01/2019.
 */

public class LocationServiceListener extends Activity implements LocationListener{

    String TAG = "DEBUG: ";
    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(getBaseContext(), "Location changed: Lat: " + location.getLatitude() + " Lng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + location.getLongitude();
        //Log.v(TAG, longitude);
        String latitude = "Latitude: " + location.getLatitude();
        //Log.v(TAG, latitude);

        /*------- To get city name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;

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
