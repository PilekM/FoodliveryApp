package com.example.foodliveryapp.log.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.R;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.log.session.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.util.HashMap;
import java.util.Map;

public class LocationService extends Service {


    private final long INTERVAL = 1000*20;
    private final long FAST_INTERVAL = 1000*30;

    private Session userSession;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient locationProviderClient;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        userSession = new Session(this);

        locationCallback = createLocationCallback();
        locationRequest = createLocationRequest();

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        notificationManager  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

        popNotification();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private LocationCallback createLocationCallback(){

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                if (locationResult == null) {
                    return;
                }

                Location lastLocation = locationResult.getLastLocation();
                double latitude = lastLocation.getLatitude();
                double longitude = lastLocation.getLongitude();

                Log.i("latitude", String.valueOf(latitude));
                Log.i("longitude", String.valueOf(longitude));
                sendLocationToBase(longitude, latitude);

            }
        };

        return locationCallback;
    }

    private LocationRequest createLocationRequest(){

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FAST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        return locationRequest;

    }

    private void popNotification(){
        int notificationId = 23;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, null)
                .setSmallIcon(R.drawable.icon_restaurant)
                .setContentTitle("FoodliveryApp")
                .setContentText("FoodliveryApp is sending location.")
                .setCategory(Notification.CATEGORY_SERVICE);


        notificationManager.notify(notificationId, mBuilder.build());
    }

    private void sendLocationToBase(double latitude, double longitude){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                Services.UPDATE_LOCATION,
                response -> {

                },
                error -> {
                    Toast.makeText(this, "Nie udało się przesłać lokalizacji do bazy", Toast.LENGTH_SHORT).show();
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("longitude", String.valueOf(longitude));
                headers.put("latitude", String.valueOf(latitude));
                headers.put("uuid", userSession.getUUID());
                return headers;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancelAll();
        locationProviderClient.removeLocationUpdates(locationCallback);
    }
}
