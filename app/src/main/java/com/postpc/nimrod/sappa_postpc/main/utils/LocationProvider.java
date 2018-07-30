package com.postpc.nimrod.sappa_postpc.main.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.subjects.SingleSubject;

public class LocationProvider {
    private static final long LOCATION_CALLBACK_DELAY_TIME = 1000;
    private Timer timer1;
    private LocationManager locationManager;
    private SingleSubject<Location> locationResult;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private Context context;
    private boolean emittedLocation = false;

    public LocationProvider(Context context) {
        this.context = context;
    }

    public boolean getLocation(SingleSubject<Location> result) {
        locationResult = result;
        if (locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled && !network_enabled)
            return false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if (network_enabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        timer1 = new Timer();
        timer1.schedule(new GetLastLocation(), LOCATION_CALLBACK_DELAY_TIME);
        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            if(!emittedLocation){
                locationResult.onSuccess(location);
                emittedLocation = true;
            }
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            if(!emittedLocation){
                locationResult.onSuccess(location);
                emittedLocation = true;
            }
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);
            Location net_loc = null, gps_loc;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(network_enabled)
                net_loc= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(gps_loc!=null && net_loc!=null){
                if(!emittedLocation){
                    if(gps_loc.getTime()>net_loc.getTime())
                        locationResult.onSuccess(gps_loc);
                    else
                        locationResult.onSuccess(net_loc);
                    emittedLocation = true;
                }
                return;
            }

            if(gps_loc!=null){
                if(!emittedLocation){
                    locationResult.onSuccess(gps_loc);
                    emittedLocation = true;
                }
                return;
            }
            if(net_loc!=null){
                if(!emittedLocation){
                    locationResult.onSuccess(net_loc);
                    emittedLocation = true;
                }
                return;
            }
            locationResult.onSuccess(net_loc);
        }
    }
}

