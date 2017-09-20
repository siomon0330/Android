package com.example.chuanli.memorableplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;



    public void centerMapLocation(Location location, String title){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        if(title != "Your location") {
            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapLocation(location, "Your location");

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);


        Intent intent = getIntent();
        if(intent.getIntExtra("number", 0) == 0) {

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    centerMapLocation(location, "Your location");

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };


            if (Build.VERSION.SDK_INT < 23) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                } else {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    centerMapLocation(location, "Your location");
                }
            }

        }else{

            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(MainActivity.locations.get(intent.getIntExtra("number", 0)).latitude);
            location.setLongitude(MainActivity.locations.get(intent.getIntExtra("number", 0)).longitude);

           centerMapLocation(location, MainActivity.places.get(intent.getIntExtra("number", 0)));

        }



    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";
        try {
            List<Address> listAdress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(listAdress != null && listAdress.size() > 0){
                if(listAdress.get(0).getThoroughfare() != null){
                    if(listAdress.get(0).getSubThoroughfare() != null){

                        address += listAdress.get(0).getSubThoroughfare()+" ";
                    }

                        address += listAdress.get(0).getThoroughfare();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(address == ""){

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyMMdd");
            address = sdf.format(new Date());


        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.chuanli.memorableplaces", Context.MODE_PRIVATE);



        try {
            ArrayList<String> lantitude = new ArrayList<>();
            ArrayList<String> longtitude = new ArrayList<>();

            for(LatLng coordinate : MainActivity.locations){
                lantitude.add(Double.toString(coordinate.latitude));
                longtitude.add(Double.toString(coordinate.longitude));
            }

            sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MainActivity.places)).apply();
            sharedPreferences.edit().putString("lantitude", ObjectSerializer.serialize(lantitude)).apply();
            sharedPreferences.edit().putString("longtitude", ObjectSerializer.serialize(longtitude)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Toast.makeText(this, "Location saved", Toast.LENGTH_LONG).show();
        MainActivity.arrayAdapter.notifyDataSetChanged();
    }
}






