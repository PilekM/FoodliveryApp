package com.example.foodliveryapp;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.orders_recycler.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class OrderHandler {
    Context ctx;

    public OrderHandler(Context ctx){
        this.ctx = ctx;
    }


    public ArrayList<Order> convertJSONtoOrders(JSONArray array){
        ArrayList<Order> orderList = new ArrayList<>();

        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject jsonOrder = array.getJSONObject(i);
                String orderTime = jsonOrder.getString("order_time");
                //TODO extract date formatter
                @SuppressLint("SimpleDateFormat") DateFormat orderDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                orderDateTime.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = orderDateTime.parse(orderTime);
                @SuppressLint("SimpleDateFormat") DateFormat targetPattern = new SimpleDateFormat("HH:mm:ss");
                targetPattern.setTimeZone(TimeZone.getDefault());

                String formattedDateTime = targetPattern.format(date);
                String street = jsonOrder.getString("street");
                String price = jsonOrder.getString("price");
                String priceType = jsonOrder.getString("price_type");
                priceType = new PriceType().getPriceType(Integer.valueOf(priceType));
                String restaurantName = jsonOrder.getString("name");
                String restaurantAddress = jsonOrder.getString("address");

                Order order = new Order(street, formattedDateTime, restaurantName, restaurantAddress, price, priceType);
                orderList.add(order);

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

        }

        return orderList;
    }

    void getOrders(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.GET_ORDERS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error from error listener: " + error);
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                Session session = new Session(ctx);

                params.put("id", session.getUUID());

                System.out.println(params);
                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }
}
