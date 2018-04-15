package com.xuchen.enums;

import java.util.HashMap;

public enum WeightTypeEnum {
    gram(0,"克"),
    kilogram(1,"千克"),
    ton(2,"吨");

    private int id;
    private String value;

    WeightTypeEnum(int id, String value){
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
        WeightTypeEnum[] values = values();
        for (WeightTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
