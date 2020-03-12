package com.example.foodliveryapp.log;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.data.order.DetailedOrder;
import com.example.foodliveryapp.data.order.OrderStatusType;
import com.example.foodliveryapp.data.price.PriceType;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailedOrderHandler {

    private Context ctx;
    private String orderNumber;
    private DetailedOrder detailedOrder;


    public DetailedOrderHandler(Context ctx, String orderNumber, DetailedOrder detailedOrder) {
        this.ctx = ctx;
        this.orderNumber = orderNumber;
        this.detailedOrder = detailedOrder;
    }


    private void convertJSONtoDetailedOrder(JSONObject response){

        try {
            if(response.getInt("success") == 1) {
                JSONArray jsonArray = response.getJSONArray("body");
                JSONObject responseBody = jsonArray.getJSONObject(0);

                int orderStatusId = responseBody.getInt("order_status");
                String orderStatus = new OrderStatusType().getOrderStatusType(orderStatusId).toUpperCase();
                String customerAddress = "ul. " + responseBody.getString("street");
                String customerName = responseBody.getString("name") + " " + responseBody.getString("surname");
                String customerPhone = responseBody.getString("client_phone");
                String customerNote = responseBody.getString("notes");
                String orderItems = responseBody.getString("products");
                String priceValue = responseBody.getString("price");
                String priceType = new PriceType().getPriceType(Integer.valueOf(responseBody.getString("price_type")));
                String restaurantAddress = responseBody.getString("address");
                String restaurantPhone = responseBody.getString("restaurant_phone");

                String officePhone = "783093123"; //TODO usunac hardcoded


                detailedOrder.setOrder(orderStatus,"PP21321/21/21", customerAddress, customerName,
                        customerPhone, customerNote, orderItems, priceValue, priceType,
                        restaurantAddress, restaurantPhone, officePhone);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //TODO wyciągnąć logikę tworzenia requesta do innej klasy
    public void getOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.GET_DETAILED_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            //TODO check here if success to not implement it multiple times
                            convertJSONtoDetailedOrder(new JSONObject(response));
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };


        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void denyOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.DENY_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void acceptOrder(final ServerCallback callback) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.ACCEPT_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }


    public void waitRestOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.WAIT_REST_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void deliverOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.DELIVER_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void driveCompletedOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.DRIVE_COMPLETED_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void deliveredOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.DELIVERED_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void notDeliveredOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.NOT_DELIVERED_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

    public void completedOrder(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.COMPLETED_ORDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id", orderNumber);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }
}
