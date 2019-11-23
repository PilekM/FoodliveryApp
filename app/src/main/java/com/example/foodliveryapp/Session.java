package com.example.foodliveryapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences prefs;
    private final String PREFERENCES_NAME = "session_preferences";

    public Session(Context ctx){
        prefs = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public boolean setUsername(String username){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("username", username);

        return prefsEditor.commit();
    }

    public boolean setPassword(String password){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("password", password);

        return prefsEditor.commit();
    }

    public boolean setUUID(String uuid){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("uuid", uuid);

        return prefsEditor.commit();
    }

    public String getUsername(){
        return prefs.getString("username", "@null");
    }
    public String getPassword(){
        return prefs.getString("password", "@null");
    }
    public String getUUID(){
        return prefs.getString("uuid", "@null");
    }

    public boolean restartSession(){
        boolean result = true;

        result &= setUsername("@null");
        result &= setPassword("@null");
        result &= setUUID("@null");

        return result;
    }
}
