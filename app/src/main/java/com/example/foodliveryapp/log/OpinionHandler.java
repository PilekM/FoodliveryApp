package com.example.foodliveryapp.log;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.foodliveryapp.R;
import com.example.foodliveryapp.data.form.OpinionForm;
import com.example.foodliveryapp.database.RequestHandler;
import com.example.foodliveryapp.database.ServerCallback;
import com.example.foodliveryapp.database.Services;
import com.example.foodliveryapp.log.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OpinionHandler {

    private Activity activity;

    private Session session;

    private OpinionForm form;

    private RatingBar atmosphereBar;
    private RatingBar salaryBar;
    private RatingBar workHourBar;
    private RatingBar officeContactBar;
    private EditText ideasEditText;
    private EditText issuesEditText;
    private Button opinionUpdateButton;

    public OpinionHandler(Activity activity){
        this.activity = activity;
        this.session = new Session(activity);
        this.form = new OpinionForm();

        atmosphereBar = activity.findViewById(R.id.opinion_atmosphere_bar);
        salaryBar = activity.findViewById(R.id.opinion_salary_bar);
        workHourBar = activity.findViewById(R.id.opinion_workhour_bar);
        officeContactBar = activity.findViewById(R.id.opinion_office_contact_bar);
        ideasEditText = activity.findViewById(R.id.opinion_ideas_et);
        issuesEditText = activity.findViewById(R.id.opinion_issues_et);
        opinionUpdateButton = activity.findViewById(R.id.opinion_update_button);

    }

    public void getOpinionForm(final ServerCallback callback){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                Services.GET_OPINION_FORM,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uuid", session.getUUID());
                return params;
            }
        };

        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }

    private void prepareForm(float atmosphereRate, float salaryRate, float workHourRate, float officeContactRate, String ideas, String issues){
        form = new OpinionForm(atmosphereRate, salaryRate, workHourRate, officeContactRate, ideas, issues);
    }

    public void fillFormFromJSON(JSONObject jsonObject){

        try {
            form.setAtmosphereRate((float)jsonObject.getDouble("opin_atmosphere"));
            form.setSalaryRate((float)jsonObject.getDouble("opin_salary"));
            form.setWorkHourRate((float)jsonObject.getDouble("opin_work_hour"));
            form.setOfficeContactRate((float)jsonObject.getDouble("opin_office_contact"));
            form.setIdeas(jsonObject.getString("ideas"));
            form.setIssues(jsonObject.getString("issues"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        fillUIForm();
    }

    public void fillFormFromUI(){
        form.setAtmosphereRate(atmosphereBar.getRating());
        form.setSalaryRate(salaryBar.getRating());
        form.setWorkHourRate(workHourBar.getRating());
        form.setOfficeContactRate(officeContactBar.getRating());
        form.setIdeas(ideasEditText.getText().toString());
        form.setIssues(issuesEditText.getText().toString());
    }

    public void fillUIForm(){
        atmosphereBar.setRating(form.getAtmosphereRate());
        atmosphereBar.setIsIndicator(true);
        salaryBar.setRating(form.getSalaryRate());
        salaryBar.setIsIndicator(true);
        workHourBar.setRating(form.getWorkHourRate());
        workHourBar.setIsIndicator(true);
        officeContactBar.setRating(form.getOfficeContactRate());
        officeContactBar.setIsIndicator(true);
        ideasEditText.setText(form.getIdeas());
        ideasEditText.setFocusable(false);
        issuesEditText.setText(form.getIssues());
        issuesEditText.setFocusable(false);
        opinionUpdateButton.setVisibility(View.INVISIBLE);

    }

    public void updateOpinion(final ServerCallback callback) {
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                Services.UPDATE_OPINION_FORM,
                response -> {
                    try {
                        callback.onSuccess(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("uuid", session.getUUID());
                params.put("atmosphere", String.valueOf(form.getAtmosphereRate()));
                params.put("salary", String.valueOf(form.getSalaryRate()));
                params.put("work_hour", String.valueOf(form.getWorkHourRate()));
                params.put("office_contact", String.valueOf(form.getOfficeContactRate()));
                params.put("ideas", form.getIdeas());
                params.put("issues", form.getIssues());

                return params;
            }
        };

        RequestHandler.getInstance(activity).addToRequestQueue(request);
    }
}
