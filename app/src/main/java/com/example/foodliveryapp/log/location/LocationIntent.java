package com.example.foodliveryapp.log.location;

import android.content.Context;
import android.content.Intent;

public class LocationIntent {

    private static Context context;

    private static LocationIntent instance;

    private Intent locationService;

    private LocationIntent(Context context){

        this.context = context;

        locationService = getLocationService();

    }

    public static synchronized LocationIntent getInstance(Context context){
        if(instance == null){
            instance = new LocationIntent(context);
        }

        return instance;
    }

    public Intent getLocationService() {
        if(locationService == null){
            locationService = new Intent(this.context, LocationService.class);
        }

        return locationService;
    }

}
