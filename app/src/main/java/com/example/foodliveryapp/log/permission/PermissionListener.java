package com.example.foodliveryapp.log.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class PermissionListener implements MultiplePermissionsListener {

    Context ctx;

    public PermissionListener(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if(!report.areAllPermissionsGranted()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage("Wszystkie pozwolenia są potrzebne do poprawnego funkcjonowania aplikacji. Dopóki nie zostaną zmienione, aplkacja nie będzię funkcjonować poprawnie.");
            builder.setPositiveButton("Ok", (dialog, which) -> {
                ((Activity)ctx).moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            });
            builder.show();
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Zmień swoje ustawienia pozwoleń lub aplikacja nie będzie mogła funkcjonować poprawnie.");
        builder.setPositiveButton("Ok", (dialog, which) -> {
            ((Activity)ctx).moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
        builder.show();
    }
}
