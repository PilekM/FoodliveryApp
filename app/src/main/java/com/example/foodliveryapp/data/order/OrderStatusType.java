package com.example.foodliveryapp.data.order;

import java.util.HashMap;

public class OrderStatusType {

    private HashMap<Integer, String> priceTypes =  new HashMap<Integer,String>(){
        {
            put(0, "Oczekuje na przyjęcie");
            put(1, "W drodzę do restauracji");
            put(2, "Oczekiwanie w restauracji");
            put(3, "Dostarczanie do klienta");
            put(4, "Finalizowanie zamówienia");
            put(5, "Zamówienie dostarczone");
            put(6, "Zamówienie niedostarczone");
            put(7, "Zamówienie zakończone");
        }
    };

    public String getOrderStatusType(int orderStatus){
        return priceTypes.get(orderStatus);
    }
}
