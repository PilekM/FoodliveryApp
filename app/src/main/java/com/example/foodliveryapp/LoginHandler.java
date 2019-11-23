package com.example.foodliveryapp;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.Services;
import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;



public class LoginHandler {

    private String login;
    private String password;
    private Context ctx;

    public LoginHandler(Context ctx){
        this.login = "";
        this.password = "";
        this.ctx = ctx;
    }


    void storeLogin(String login){
        this.login = login; //TODO add some string checks
        System.out.println(login);
    }

    void storePassword(String password){
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString(); //TODO check passwor
        System.out.println(password);
    }

    void storeHashedPassword(String password){
        this.password = password;
    }

    String getLogin(){
        return this.login;
    }

    String getPassword(){
        return this.password;
    }


    void checkCredentials(final ServerCallback callback){


        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject responseJson = new JSONObject(response);
                            callback.onSuccess(responseJson);
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();

                params.put("username", login);
                params.put("password", password);

                return params;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(request);
    }

}
