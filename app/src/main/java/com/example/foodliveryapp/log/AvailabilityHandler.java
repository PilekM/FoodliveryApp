package com.example.foodliveryapp.log;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.R;
import com.example.foodliveryapp.data.form.AvailabilityForm;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.log.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AvailabilityHandler {

    private AvailabilityForm form;

    private Activity activity;

    private Session session;

    private EditText monday;
    private EditText tuesday;
    private EditText wednesday;
    private EditText thursday;
    private EditText friday;
    private EditText saturday;
    private EditText sunday;

    public AvailabilityHandler(Activity activity){
        this.activity = activity;
        this.session = new Session(activity);


        monday = activity.findViewById(R.id.availability_monday_et);
        tuesday = activity.findViewById(R.id.availability_tuesday_et);
        wednesday = activity.findViewById(R.id.availability_wednesday_et);
        thursday = activity.findViewById(R.id.availability_thursday_et);
        friday = activity.findViewById(R.id.availability_friday_et);
        saturday = activity.findViewById(R.id.availability_saturday_et);
        sunday = activity.findViewById(R.id.availability_sunday_et);

    }

    private void prepareForm(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday){
        form = new AvailabilityForm(monday, tuesday, wednesday, thursday, friday, saturday, sunday);

    }

    public void clearFormText(){
        monday.setText(null);
        tuesday.setText(null);
        wednesday.setText(null);
        thursday.setText(null);
        friday.setText(null);
        saturday.setText(null);
        sunday.setText(null);
    }

    public void fillFormFromJSON(JSONObject object) throws JSONException {

        String monday = object.getString("monday");
        String tuesday = object.getString("tuesday");
        String wednesday = object.getString("wednesday");
        String thursday = object.getString("thursday");
        String friday = object.getString("friday");
        String saturday = object.getString("saturday");
        String sunday = object. getString("sunday");

        prepareForm(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        fillUIForm();
    }

    public void fillUIForm(){
        setHint(monday, form.getMonday());
        setHint(tuesday, form.getTuesday());
        setHint(wednesday, form.getWednesday());
        setHint(thursday, form.getThursday());
        setHint(friday, form.getFriday());
        setHint(saturday, form.getSaturday());
        setHint(sunday, form.getSunday());
    }

    private void setHint(EditText et, String text){
        if(!text.equals("null")){
            et.setHint(text);
        }
    }

    public void fillFormFromUI(){

        form.setMonday(getDayFromEditText(monday));
        form.setTuesday(getDayFromEditText(tuesday));
        form.setWednesday(getDayFromEditText(wednesday));
        form.setThursday(getDayFromEditText(thursday));
        form.setFriday(getDayFromEditText(friday));
        form.setSaturday(getDayFromEditText(saturday));
        form.setSunday(getDayFromEditText(sunday));
        
    }

    private String getDayFromEditText(EditText et){
        if(!et.getText().toString().equals("") || et.getHint().equals(activity.getResources().getString(R.string.availability_hint))){
            return et.getText().toString();
        }else{
            return et.getHint().toString();
        }
    }


    public void sendForm(final ServerCallback callback){

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                Services.UPDATE_AVAILABILITY_FORM,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Toast.makeText(activity, "Nie udało się pobrać danych", Toast.LENGTH_SHORT).show();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("monday", form.getMonday());
                headers.put("tuesday", form.getTuesday());
                headers.put("wednesday", form.getWednesday());
                headers.put("thursday", form.getThursday());
                headers.put("friday", form.getFriday());
                headers.put("saturday", form.getSaturday());
                headers.put("sunday", form.getSunday());
                headers.put("uuid", session.getUUID());

                return headers;
            }
        };

        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }

    public void getForm(final ServerCallback callback){

        StringRequest request = new StringRequest(
                Request.Method.GET,
                Services.GET_AVAILABILITY_FORM,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Toast.makeText(activity, "Nie udało się pobrać danych", Toast.LENGTH_SHORT).show();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("uuid", session.getUUID());

                return headers;
            }
        };

        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }


}
