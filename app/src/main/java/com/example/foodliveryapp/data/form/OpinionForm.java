package com.example.foodliveryapp.data.form;

public class OpinionForm {

    private float atmosphereRate;
    private float salaryRate;
    private float workHourRate;
    private float officeContactRate;
    private String ideas;
    private String issues;

    public OpinionForm(){
        this.atmosphereRate = 0.0f;
        this.salaryRate = 0.0f;
        this.workHourRate = 0.0f;
        this.officeContactRate = 0.0f;
        this.ideas = "";
        this.issues = "";
    }

    public OpinionForm(float atmosphereRate, float salaryRate, float workHourRate, float officeContactRate, String ideas, String issues) {
        this.atmosphereRate = atmosphereRate;
        this.salaryRate = salaryRate;
        this.workHourRate = workHourRate;
        this.officeContactRate = officeContactRate;
        this.ideas = ideas;
        this.issues = issues;
    }

    public float getAtmosphereRate() {
        return atmosphereRate;
    }

    public void setAtmosphereRate(float atmosphereRate) {
        this.atmosphereRate = atmosphereRate;
    }

    public float getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(float salaryRate) {
        this.salaryRate = salaryRate;
    }

    public float getWorkHourRate() {
        return workHourRate;
    }

    public void setWorkHourRate(float workHourRate) {
        this.workHourRate = workHourRate;
    }

    public float getOfficeContactRate() {
        return officeContactRate;
    }

    public void setOfficeContactRate(float officeContactRate) {
        this.officeContactRate = officeContactRate;
    }

    public String getIdeas() {
        return ideas;
    }

    public void setIdeas(String ideas) {
        this.ideas = ideas;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }
}
