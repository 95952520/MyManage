package com.xuchen.enums;

import java.util.HashMap;

public enum StockTypeEnum {
    goods(0,"商品"),
    stock(1,"原料");

    private int id;
    private String value;

    StockTypeEnum(int id, String value){
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
        StockTypeEnum[] values = values();
        for (StockTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }
}
