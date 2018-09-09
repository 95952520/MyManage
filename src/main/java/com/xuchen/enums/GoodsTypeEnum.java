package com.xuchen.enums;

import java.util.HashMap;

public enum GoodsTypeEnum {
    POWDER(0, "粉类"),
    SLURRY(1, "浆类"),
    EMULSION(2, "乳液类"),
    SAND(3, "沙类"),
    APPLIANCES(4, "器具类"),
    OTHERS(5, "其他类别");


    private int id;
    private String value;

    GoodsTypeEnum(int id, String value){
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
        GoodsTypeEnum[] values = values();
        for (GoodsTypeEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
