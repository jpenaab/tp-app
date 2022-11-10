package org.hopto.tiempoplaya.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.hopto.tiempoplaya.modelo.TPlayas;
import org.hopto.tiempoplaya.ws.PlayaWSGetNearest;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import uk.me.jstott.jcoord.UTMRef;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    List<TPlayas> listPlayas = new ArrayList<TPlayas>();
    private String tk;
    private String userUtmx;
    private String userUtmy;
    private String userUtmz;
    private String utmzone;

    private GoogleMap mMap;

    private TPlayas tPlayas;

    public TPlayas gettPlayas() {
        return tPlayas;
    }

    public void settPlayas(TPlayas tPlayas) {
        this.tPlayas = tPlayas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        tk = getIntent().getStringExtra("tk");
        userUtmx = getIntent().getStringExtra("utmx");
        userUtmy = getIntent().getStringExtra("utmy");
        userUtmz = getIntent().getStringExtra("utmz");
        utmzone = getIntent().getStringExtra("utmr");

        PlayaWSGetNearest playaWSGetFiveNearest = new PlayaWSGetNearest();
        playaWSGetFiveNearest.execute(tk, userUtmx, userUtmy, userUtmz, utmzone);

        try {

            listPlayas = playaWSGetFiveNearest.get();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

        mMap = googleMap;

        LatLng latLng = null;

        for (int i = 0; i < listPlayas.size(); ++i) {

            tPlayas = new TPlayas();
            settPlayas(listPlayas.get(i));

            Double utmx = null;
            Double utmy = null;
            try {
                utmx = DecimalFormat.getNumberInstance().parse(gettPlayas().getCoordUTMx()).doubleValue();
                utmy = DecimalFormat.getNumberInstance().parse(gettPlayas().getCoordUTMy()).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            UTMRef utmRef = new UTMRef(utmx, utmy, 'R', Integer.valueOf(gettPlayas().getCoordUTMz()));
            uk.me.jstott.jcoord.LatLng l = utmRef.toLatLng();
            latLng = new LatLng(l.getLat(), l.getLng());


            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.draggable(false);
            markerOptions.title(gettPlayas().getNombre());
            markerOptions.snippet(getResources().getString(R.string.info_beach_gps));
            mMap.addMarker(markerOptions);

            //listener
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){

                                                  @Override
                                                  public void onInfoWindowClick(Marker marker) {
                                                      Intent intent = new Intent(getApplicationContext(), StatusPlayaActivity.class);
                                                      intent.putExtra("nombrePlaya", marker.getTitle());
                                                      intent.putExtra("tk", tk);
                                                      startActivity(intent);
                                                      //Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
                                                  }
                                              }
            );


        }

        //user Position
        LatLng latLngUser;

        Double userUTMx = Double.valueOf(userUtmx);
        Double userUTMy = Double.valueOf(userUtmy);
        Integer userUTMz = Integer.valueOf(userUtmz);

        UTMRef utmRef = new UTMRef(userUTMx, userUTMy, 'R', userUTMz);
        uk.me.jstott.jcoord.LatLng lUser = utmRef.toLatLng();
        latLngUser = new LatLng(lUser.getLat(), lUser.getLng());

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngUser));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, 10));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

}
