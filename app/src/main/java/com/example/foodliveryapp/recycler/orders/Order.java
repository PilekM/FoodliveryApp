package com.example.foodliveryapp.recycler.orders;

public class Order {


    private int orderStatus;
    private String orderNumber;
    private String orderAddress;
    private String orderTime;
    private String restaurantName;
    private String restaurantAddress;
    private String price;
    private String priceType;


    public Order(int orderStatus, String orderNumber, String orderAddress, String orderTime, String restaurantName, String restaurantAddress, String price, String priceType) {
        this.orderStatus = orderStatus;
        this.orderNumber = orderNumber;
        this.orderAddress = orderAddress;
        this.orderTime = orderTime;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.price = price;
        this.priceType = priceType;
    }


    int getOrderStatus() {return orderStatus;}

    String getOrderAddress() {
        return orderAddress;
    }

    String getOrderTime() {
        return orderTime;
    }

    String getRestaurantName() {
        return restaurantName;
    }

    String getRestaurantAddress() {
        return restaurantAddress;
    }

    String getPrice() {
        return price;
    }

    String getPriceType() {
        return priceType;
    }

    String getOrderNumber(){ return orderNumber; }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}
