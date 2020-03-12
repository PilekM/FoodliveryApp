package com.example.foodliveryapp.log.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

public class GPSandInternetChecker {

    private Activity act;

    private LocationManager locationManager;
    private ConnectivityManager connectivityManager;




    public GPSandInternetChecker(Activity act) {

        this.act = act;
        this.locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
        this.connectivityManager = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void checkGPS() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

    }

    private void checkInternet() {
        if(connectivityManager.getActiveNetwork() == null){
            buildAlertMessageNoInternet();
        }
    }

    public void checkRequiredConnections() {
        checkGPS();
        checkInternet();
    }


    private void buildAlertMessage(String message, Intent settingsIntent){
        final AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Tak", (dialog, id) -> act.startActivity(settingsIntent))
                .setNegativeButton("Nie", (dialog, id) -> act.finishAndRemoveTask());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertMessageNoGps() {
        Intent gpsSettsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        String message = "GPS jest wyłączony. Aplikacja nie jest w stanie bez niego funkcjonować. Czy chcesz zmienić ustawienia?";
        buildAlertMessage(message,gpsSettsIntent);
    }

    private void buildAlertMessageNoInternet(){
        Intent internetSettsIntent = new Intent(Settings.ACTION_SETTINGS);
        String message = "Internet jest wyłączony. Aplikacja nie jest w stanie bez niego działać. Chcesz zmienić ustawienia?";
        buildAlertMessage(message,internetSettsIntent);
    }


}

