package com.xuchen.enums;

import java.util.HashMap;

public enum DeliveryTypeEnum {
    noDelivery(0,"上门取货"),
    deliverSelf(1,"自己配送"),
    thirdDelivery(2,"找人配送");

    private int id;
    private String value;

    DeliveryTypeEnum(int id, String value){
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public static HashMap<Integer,String> getMap(){
        HashMap<Integer,String> map=new HashMap<>();
        DeliveryTypeEnum[] values = values();
        for (DeliveryTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
