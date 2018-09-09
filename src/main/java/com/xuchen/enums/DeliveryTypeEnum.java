package com.xuchen.enums;

import java.util.HashMap;

public enum DeliveryTypeEnum {
    NO_DELIVERY(0,"客户上门"),
    DELIVER_SELF(1,"自家取送"),
    THIRD_DELIVERY(2,"他人取送");

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
