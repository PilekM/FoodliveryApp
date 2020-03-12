package com.example.foodliveryapp.database;

import org.json.JSONException;
import org.json.JSONObject;

public interface ServerCallback {

    void onSuccess(JSONObject response) throws JSONException;

}

