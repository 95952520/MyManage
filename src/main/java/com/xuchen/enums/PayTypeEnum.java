package com.xuchen.enums;

import java.util.HashMap;

public enum PayTypeEnum {
    noPay(0,"未付款"),
    partPay(1,"付部分"),
    allPay(2,"已付款");

    private int id;
    private String value;

    PayTypeEnum(int id, String value){
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
        PayTypeEnum[] values = values();
        for (PayTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
