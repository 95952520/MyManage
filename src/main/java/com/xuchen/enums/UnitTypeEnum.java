package com.xuchen.enums;

import java.util.HashMap;

public enum UnitTypeEnum {
    BAG(0,"袋"),
    BUCKET(1,"桶"),
    BOTTLE(2,"瓶"),
    COUNT(3,"个"),
    KILOGRAM(4,"公斤");
    private int id;
    private String value;

    UnitTypeEnum(int id, String value){
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
        UnitTypeEnum[] values = values();
        for (UnitTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
