package com.xuchen.enums;

import java.util.HashMap;

public enum UserTypeEnums {

    HOME(0, "自家"),
    SHOP(1, "门店"),
    WORKER(2, "涂料工"),
    FACTORY(3, "工厂"),
    LITTLE_BUYER(4, "散客"),
    SUPPLIER(5, "供应商"),
    DELIVER(6, "配送员"),
    ELSE_TYPE(7, "其他");

    private int id;
    private String value;

    UserTypeEnums(int id, String value){
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
        UserTypeEnums[] values = values();
        for (UserTypeEnums enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }
}
