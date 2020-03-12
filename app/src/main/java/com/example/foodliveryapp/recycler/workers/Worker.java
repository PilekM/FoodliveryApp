package com.example.foodliveryapp.recycler.workers;

public class Worker {
    private String name;
    private String surname;
    private String phoneNumber;
    private boolean active;

    public Worker(String name, String surname, String phoneNumber, boolean active) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
