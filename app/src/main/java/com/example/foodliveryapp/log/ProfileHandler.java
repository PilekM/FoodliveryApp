package com.example.foodliveryapp.log;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.R;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.log.session.Session;
import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileHandler {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatPassword;

    private String hashedOldPassword;
    private String hashedNewPassword;

    private EditText oldPhone;
    private EditText newPhone;

    private Activity activity;
    private Session session;

    public ProfileHandler(Activity activity) {
        this.activity = activity;

        oldPassword = this.activity.findViewById(R.id.profile_old_pass_et);
        newPassword = this.activity.findViewById(R.id.profile_new_pass_et);
        repeatPassword = this.activity.findViewById(R.id.profile_repeat_pass_et);

        oldPhone = this.activity.findViewById(R.id.profile_old_phone_et);
        newPhone = this.activity.findViewById(R.id.profile_new_phone_et);

        session = new Session(this.activity);

    }

    public boolean checkPasswords() {
        if (!checkPassword(oldPassword)) {
            oldPassword.setError("Hasło ma niepoprawny format.");
            resetPasswords();
            return false;
        }

        if (!checkPassword(newPassword)) {
            newPassword.setError("Hasło musi zawierać jedną małą, jedną dużą literę, cyfrę, znak specjalny oraz minimum 8 znaków.");
            resetPasswords();
            return false;
        }

        if (!checkPassword(repeatPassword)) {
            oldPassword.setError("Hasło musi zawierać jedną małą, jedną dużą literę, cyfrę, znak specjalny oraz minimum 8 znaków.");
            resetPasswords();
            return false;
        }
        if (!newPassword.getText().toString().equals(repeatPassword.getText().toString())) {
            newPassword.setError("Hasła nie są takie same.");
            resetPasswords();
            return false;
        }


        storePasswords();
        return true;
    }

    private boolean checkPhone(EditText et){
        String phoneRegex = "\\d{9}";
        return et.getText().toString().matches(phoneRegex);
    }

    public boolean checkPhones(){
        boolean result = true;
        if(!checkPhone(oldPhone)){
            oldPhone.setError("Numer ma niepoprawny format.");
            oldPhone.setText("");
            result = false;
        }

        if(!checkPhone(newPhone)){
            newPhone.setError("Numer ma niepoprawny format.");
            newPhone.setText("");
            result = false;
        }

        return result;
    }

    public void resetPasswords() {
        oldPassword.setText("");
        newPassword.setText("");
        repeatPassword.setText("");
    }

    public void resetPhones() {
        oldPhone.setText("");
        newPhone.setText("");
    }

    private void storePasswords() {
        hashedOldPassword = Hashing.sha256().hashString(oldPassword.getText().toString(), StandardCharsets.UTF_8).toString();
        hashedNewPassword = Hashing.sha256().hashString(newPassword.getText().toString(), StandardCharsets.UTF_8).toString();
    }

    private boolean checkPassword(EditText et) {
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        return et.getText().toString().matches(passwordRegex);
    }

    public void sendPassword(final ServerCallback callback) {
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                Services.UPDATE_PASSWORD,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.d("Error", error.getMessage());

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uuid", session.getUUID());
                params.put("old_password", hashedOldPassword);
                params.put("new_password", hashedNewPassword);
                return params;
            }
        };

        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }

    public void sendPhone(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                Services.UPDATE_PHONE,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.d("Error", error.getMessage());

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uuid", session.getUUID());
                params.put("old_phone", oldPhone.getText().toString());
                params.put("new_phone", newPhone.getText().toString());
                return params;
            }
        };

        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }
}
