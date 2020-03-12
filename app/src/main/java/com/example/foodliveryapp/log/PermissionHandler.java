package com.example.foodliveryapp.log;

import android.Manifest;
import android.app.Activity;

import com.example.foodliveryapp.log.permission.PermissionListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.karumi.dexter.Dexter;

public class PermissionHandler {
    private Activity act;
    private PermissionListener permissionListener;

    public PermissionHandler(Activity act){
        this.act = act;
        permissionListener = new PermissionListener(act);
    }

    public void handlePermissions(){
        Dexter.withActivity(act).
                withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        ,Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(permissionListener).check();
    }

    public boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(act);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(act, resultCode, 9000);
            } else {
                act.finishAndRemoveTask();
             }
            return false;
        }
        return true;
    }
}
