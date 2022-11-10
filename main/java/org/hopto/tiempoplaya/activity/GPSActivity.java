package org.hopto.tiempoplaya.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.ws.PlayaWSGetClosest;

import java.util.concurrent.ExecutionException;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

/**
 * Created by jpenaab on 04/01/2019.
 */

public class GPSActivity extends Activity {

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private static final int INITIAL_REQUEST=1337;

    private Button btnActualizar;
    private Button btnDesactivar;
    private TextView lblLatitud;
    private TextView lblLongitud;
    private TextView lblPrecision;
    private TextView lblEstado;

    private LocationManager locManager;
    private LocationListener locListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        btnActualizar = (Button) findViewById(R.id.BtnActualizar);
        btnDesactivar = (Button) findViewById(R.id.BtnDesactivar);
        lblLatitud = (TextView) findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblPrecision = (TextView) findViewById(R.id.LblPosPrecision);
        lblEstado = (TextView) findViewById(R.id.LblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                comenzarLocalizacion();
            }
        });

        btnDesactivar.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });

        if (!canAccessLocation()) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }
    }

    @SuppressLint("MissingPermission")
    private void comenzarLocalizacion() {
        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                lblEstado.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                lblEstado.setText("Provider ON ");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                //Log.i("", "Provider Status: " + status);
                lblEstado.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
    }

    private void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            //Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));

            uk.me.jstott.jcoord.LatLng coordinateSystem = new LatLng(loc.getLatitude(), loc.getLongitude());
            UTMRef utmRef = coordinateSystem.toUTMRef();

            //Log.i("DEBUG: UTM: ",utmRef.getEasting() + " " + utmRef.getNorthing() + " " + utmRef.getLngZone());

            PlayaWSGetClosest p = new PlayaWSGetClosest();
            p.execute(String.valueOf(utmRef.getEasting()), String.valueOf(utmRef.getNorthing()), String.valueOf(utmRef.getLngZone()));

            TPlayas playa  = null;

            try {
                //get the computed result.
                playa = (TPlayas) p.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            lblEstado.setText(playa.toString());
        }
        else
        {
            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPrecision.setText("Precision: (sin_datos)");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }
}
