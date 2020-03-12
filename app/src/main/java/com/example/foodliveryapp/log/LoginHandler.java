package com.example.foodliveryapp.log;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
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


    public void storeLogin(String login){
        this.login = login;
        System.out.println(login);
    }

    public void storePassword(String password){
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        System.out.println(password);
    }

    public void storeHashedPassword(String password){
        this.password = password;
    }

    public String getLogin(){
        return this.login;
    }

    public String getPassword(){
        return this.password;
    }


    public void checkCredentials(final ServerCallback callback){


        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.LOGIN,
                response -> {
                    try {
                        System.out.println(response);
                        JSONObject responseJson = new JSONObject(response);
                        callback.onSuccess(responseJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> System.out.println("Error from error listener: " + error)
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
