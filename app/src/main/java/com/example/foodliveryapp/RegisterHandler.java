package com.example.foodliveryapp;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterHandler {

    private Context appContext;
    private RegisterForm form;
    private Activity activity;

    RegisterHandler(Context context){
        appContext = context;
    }

    public void setRegisterForm(RegisterForm form){
        this.form = form;
    }

    public void sendRegisterForm(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Services.REGISTER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject responseJson = new JSONObject(response);
                            //handleRegisterResponse(responseJson);
                            callback.onSuccess(responseJson);
                            if(responseJson.getInt("success") != 1){
                                System.out.println("Rejestracja nieudana");
                            }else{
                                System.out.println("Poprawna rejestracja");
                            }
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

                params.put("username", form.getUsername());
                params.put("password", form.getPassword());
                params.put("name", form.getName());
                params.put("surname", form.getSurname());
                params.put("email", form.getEmail());
                params.put("phone", form.getPhoneNumber());

                return params;
            }
        };

        RequestHandler.getInstance(appContext).addToRequestQueue(request);

    }

    boolean validateRegisterForm(){

        boolean correctFormat = true;

        if(!form.checkName()){
            EditText registerNameEditText = activity.findViewById(R.id.register_name);
            registerNameEditText.setText("");
            registerNameEditText.setError("Imię powinno zawierać tylko litery.");
            correctFormat = false;
        }
        if(!form.checkSurname()){
            EditText registerSurnameEditText = activity.findViewById(R.id.register_surname);
            registerSurnameEditText.setText("");
            registerSurnameEditText.setError("Nazwisko powinno zawierać tylko litery.");
            correctFormat = false;
        }
        if(!form.checkUsername()){
            EditText registerUsernameEditText = activity.findViewById(R.id.register_username);
            registerUsernameEditText.setText("");
            registerUsernameEditText.setError("Login może zawierać tylko litery, cyfry oraz '_'");
            correctFormat = false;
        }
        if(!form.checkPassword()){
            EditText registerPasswordEditText = activity.findViewById(R.id.register_password);
            registerPasswordEditText.setText("");
            registerPasswordEditText.setError("Hasło musi zawierać jedną małą, jedną dużą literę, cyfrę, znak specjalny oraz minimum 8 znaków.");
            correctFormat = false;
        }
        if(!form.checkPhone()){
            EditText registerPhoneEditText = activity.findViewById(R.id.register_phone);
            registerPhoneEditText.setText("");
            registerPhoneEditText.setError("Numer telefonu musi być podany w formacie 123456789 .");
            correctFormat = false;

        }
        if(!form.checkEmail()){
            EditText registerEmailEditText = activity.findViewById(R.id.register_email);
            registerEmailEditText.setText("");
            registerEmailEditText.setError("Niepoprawny format email.");
            correctFormat = false;
        }
        if(!form.isChecked()){
            CheckBox registerCheckbox = activity.findViewById(R.id.register_checkbox);
            registerCheckbox.setError("Wyrażenie zgody jest wymagane.");
            correctFormat = false;
        }

        return correctFormat;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
