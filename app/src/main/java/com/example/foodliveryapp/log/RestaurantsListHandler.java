package com.example.foodliveryapp.log;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.recycler.restaurants.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantsListHandler {
    private Context ctx;

    public RestaurantsListHandler(Context ctx){
        this.ctx = ctx;
    }

    public ArrayList<Restaurant> convertJSONToRestaurants(JSONArray array){
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonRestaurant = array.getJSONObject(i);
                String name = jsonRestaurant.getString("name");
                String address = jsonRestaurant.getString("address");
                String phoneNumber = jsonRestaurant.getString("restaurant_phone");

                Restaurant restaurant = new Restaurant(name, address, phoneNumber);
                restaurants.add(restaurant);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return restaurants;
    }


    public void getRestaurants(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                Services.GET_RESTAURANTS,
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
