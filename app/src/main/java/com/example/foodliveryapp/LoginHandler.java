package com.example.foodliveryapp;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.Services;
import com.google.common.hash.Hashing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class LoginHandler {

    private String login;
    private String password;

    public LoginHandler(){
        this.login = "";
        this.password = "";
    }


    void storeLogin(TextView loginText){
        //this.login = Hashing.sha256().hashString(login, StandardCharsets.UTF_8).toString(); //TODO add some string checks
        this.login = loginText.getText().toString();
        System.out.println(login);
    }

    void storePassword(TextView passwordText){
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString(); //TODO check password
        this.password = passwordText.getText().toString();
        System.out.println(password);
    }


    boolean checkCredentials(Context ctx){

        final HashMap<String, String> response = new HashMap<>();

        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject responseJson = new JSONObject(response.replace("mysqli",""));
                            if(responseJson.getInt("success") != 1){
                                System.out.println("Błąd: " + responseJson.getString("message"));
                            }else{
                                System.out.println("Poprawne logowankie.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();

                params.put("username", login);
                params.put("password", password);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
        return true;
    }

}
