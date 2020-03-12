package com.example.foodliveryapp.log;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.R;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.log.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyStatsHandler {
    private Activity activity;
    private Session session;
    private View view;

    private TextView ordersTextView;
    private TextView ordersU30TextView;
    private TextView cashTextView;
    private TextView cardTextView;
    private TextView onlineTextView;

    private TextView firstRestName;
    private TextView secondRestName;
    private TextView thirdRestName;

    private TextView firstRestValue;
    private TextView secondRestValue;
    private TextView thirdRestValue;

    public MonthlyStatsHandler(Activity activity, View view){
        this.activity = activity;

        this.session = new Session(activity);

        ordersTextView = view.findViewById(R.id.monthly_stats_orders_tv);
        ordersU30TextView = view.findViewById(R.id.monthly_stats_orders_u30_tv);
        cashTextView = view.findViewById(R.id.monthly_stats_cash_tv);
        cardTextView = view.findViewById(R.id.monthly_stats_card_tv);
        onlineTextView = view.findViewById(R.id.monthly_stats_online_tv);

        firstRestName = view.findViewById(R.id.monthly_stats_first_rest_name_tv);
        secondRestName = view.findViewById(R.id.monthly_stats_second_rest_name_tv);
        thirdRestName = view.findViewById(R.id.monthly_stats_third_rest_name_tv);

        firstRestValue = view.findViewById(R.id.monthly_stats_first_rest_value_tv);
        secondRestValue = view.findViewById(R.id.monthly_stats_second_rest_value_tv);
        thirdRestValue = view.findViewById(R.id.monthly_stats_third_rest_value_tv);
    }

    public void setStats(JSONObject object){
        try {
            setText(ordersTextView, object.getString("orders_v30"));
            setText(ordersU30TextView, object.getString("orders_u30"));
            setText(cashTextView, object.getString("cash"));
            setText(cardTextView, object.getString("card"));
            setText(onlineTextView, object.getString("online"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setRestaurants(JSONArray jsonArray){
        ArrayList<TextView> restaurantNames = new ArrayList<>();
        restaurantNames.add(firstRestName);
        restaurantNames.add(secondRestName);
        restaurantNames.add(thirdRestName);

        ArrayList<TextView> restValues = new ArrayList<>();
        restValues.add(firstRestValue);
        restValues.add(secondRestValue);
        restValues.add(thirdRestValue);

        for(int i = 0; i < restaurantNames.size(); i++){
            if(jsonArray.length() > i) {
                try {
                    JSONObject row = jsonArray.getJSONObject(i);
                    restaurantNames.get(i).setText(row.getString("name"));
                    restValues.get(i).setText(row.getString("amount"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                restaurantNames.get(i).setText("Brak danych");
                restValues.get(i).setText("-");
            }
        }
    }

    private void setText(TextView et, String text){
        if(text.equals("null")){
            et.setText("0");
        }else{
            et.setText(text);
        }
    }

    public void getMonthlyStats(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                Services.GET_MONTHLY_STATS,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        Log.e("MonthlyStatsHandlerJSON", e.getMessage());
                    }
                },
                error -> {
                    Log.e("getMonthlyStats error", error.toString());
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uuid", session.getUUID());
                return params;
            }
        };
        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }

    public void getMonthlyTopRest(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                Services.GET_MONTHLY_TOP_REST,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        Log.e("MonthStatsHandler JSON", e.getMessage());
                    }
                },
                error -> {
                    Log.e("getMonthStats error", error.toString());
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uuid", session.getUUID());
                return params;
            }
        };
        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }
}
