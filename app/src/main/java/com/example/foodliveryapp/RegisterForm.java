package com.example.foodliveryapp;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class RegisterForm {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private boolean isChecked;

    boolean checkEmail(){

        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

        return email.matches(emailRegex);
    }

    boolean checkPhone(){

        String phoneRegex = "\\d{9}";

        return phoneNumber.matches(phoneRegex);
    }

    boolean checkPassword(){

        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        return password.matches(passwordRegex);
    }

    boolean checkUsername() {

        String usernameRegex = "\\w{4,20}";

        return username.matches(usernameRegex);
    }

    boolean checkName(){

        String nameRegex = "[a-zA-z]{1,20}";

        return name.matches(nameRegex);
    }

    boolean checkSurname(){

        String surnameRegex = "[a-zA-z]{1,30}";

        return surname.matches(surnameRegex);
    }

    private String formatPersonalData(String data){
        data = data.toLowerCase();
        return data.replaceFirst(String.valueOf(data.charAt(0)), String.valueOf(data.charAt(0)).toUpperCase());
    }

    private String hashData(String data){
        return Hashing.sha256().hashString(data, StandardCharsets.UTF_8).toString();
    }

    public void prepareFormToSend(){
        this.name = formatPersonalData(this.name);
        this.surname = formatPersonalData(this.surname);
        this.password = hashData(this.password);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
