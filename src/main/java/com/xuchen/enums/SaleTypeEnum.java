package com.xuchen.enums;

import java.util.HashMap;

public enum SaleTypeEnum {
    RESELL(0,"转卖"),
    PRODUCT(1,"生产"),
    UNSALEABLE(2,"原料，非卖品");

    private int id;
    private String value;

    SaleTypeEnum(int id, String value){
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
        SaleTypeEnum[] values = values();
        for (SaleTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
