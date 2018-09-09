package com.xuchen.enums;

import java.util.HashMap;

public enum OrderTypeEnum {
    UNDELIVER(0,"未配送"),
    DELIVERING(1,"配送中"),
    FINISHED(2,"配送完成");

    private int id;
    private String value;

    OrderTypeEnum(int id, String value){
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
        OrderTypeEnum[] values = values();
        for (OrderTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
