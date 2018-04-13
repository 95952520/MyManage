package com.xuchen.enums;

import java.util.HashMap;

public enum UserStatusEnum {
    useable(1,"正常"),
    invalid(0,"失效");
    private int id;
    private String value;

    UserStatusEnum(int id, String value){
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
        UserStatusEnum[] values = values();
        for (UserStatusEnum enums : values) {
            map.put(enums.id,enums.value);
        }
        return map;
    }

}
