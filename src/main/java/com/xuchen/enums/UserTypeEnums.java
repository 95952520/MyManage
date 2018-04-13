package com.xuchen.enums;

import java.util.HashMap;

public enum UserTypeEnums {

    //0.自家 1.门店 2.涂料工 3.工厂 4.散客 5.供应商 6.其他
    home(0, "自家"),
    shop(1, "门店"),
    worker(2, "涂料工"),
    factory(3, "工厂"),
    littleBuyer(4, "散客"),
    supplier(5, "供应商"),
    elseType(6, "其他");

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
