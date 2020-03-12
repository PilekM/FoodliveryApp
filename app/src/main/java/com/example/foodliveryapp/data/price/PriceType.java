package com.example.foodliveryapp.data.price;

import java.util.HashMap;

public class PriceType {
    private HashMap<Integer, String> priceTypes =  new HashMap<Integer,String>(){
        {
            put(0, "Gotówka");
            put(1, "Karta");
            put(2, "Online");
        }
    };

    public String getPriceType(int priceType){
        return priceTypes.get(priceType);
    }
}
