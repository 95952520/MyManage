package com.xuchen.enums;

import java.util.HashMap;

public enum ProductTypeEnum {
    resell(0,"转卖"),
    product(1,"生产");

    private int id;
    private String value;

    ProductTypeEnum(int id, String value){
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
        ProductTypeEnum[] values = values();
        for (ProductTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
