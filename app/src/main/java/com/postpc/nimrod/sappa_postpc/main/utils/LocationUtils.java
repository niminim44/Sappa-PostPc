package com.postpc.nimrod.sappa_postpc.main.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationUtils {

    private final FusedLocationProviderClient mFusedLocationClient;
    private final Activity activity;
    private final Context context;

    public LocationUtils(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    public void getDeviceLocation(OnLocationReceivedCallback onLocationReceivedCallback){
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            //TODO - should we show some explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            onLocationReceivedCallback.onLocationReceived(location);
                        }
                    });
        }
    }

    public interface OnLocationReceivedCallback{

        void onLocationReceived(Location location);

    }

}
