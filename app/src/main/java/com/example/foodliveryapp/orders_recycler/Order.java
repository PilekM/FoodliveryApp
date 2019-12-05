package com.example.foodliveryapp.orders_recycler;

public class Order {


    private String orderAddress;
    private String orderTime;
    private String restaurantName;
    private String restaurantAddress;
    private String price;
    private String priceType;

    public Order(String orderAddress, String orderTime, String restaurantName, String restaurantAddress, String price, String priceType) {
        this.orderAddress = orderAddress;
        this.orderTime = orderTime;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.price = price;
        this.priceType = priceType;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceType() {
        return priceType;
    }

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
