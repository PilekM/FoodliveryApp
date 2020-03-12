package com.example.foodliveryapp.log;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.recycler.workers.Worker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorkersListHandler {

    private Context ctx;

    public WorkersListHandler(Context ctx){
        this.ctx = ctx;
    }

    public ArrayList<Worker> convertJSONToWorkers(JSONArray array){

        ArrayList<Worker> workers = new ArrayList<>();

        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject jsonWorker = array.getJSONObject(i);
                String name = jsonWorker.getString("name");
                String surname = jsonWorker.getString("surname");
                String phoneNumber = jsonWorker.getString("phone_number");

                Worker worker = new Worker(name,surname,phoneNumber,true);
                workers.add(worker);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return workers;
    }

    public void getWorkers(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                Services.GET_WORKERS,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> System.out.println("Error from listener " + error.getMessage())
        );

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }
}
